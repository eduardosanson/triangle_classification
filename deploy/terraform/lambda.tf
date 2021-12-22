resource "aws_iam_role" "lambda_role" {
  name   = "iam_role_lambda_function"
  assume_role_policy = <<EOF
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Action": "sts:AssumeRole",
      "Principal": {
        "Service": "lambda.amazonaws.com"
      },
      "Effect": "Allow",
      "Sid": ""
    }
  ]
}
EOF
}

# IAM policy for logging from a lambda

resource "aws_iam_policy" "logging" {

  name         = "iam_policy_lambda_logging_function"
  path         = "/"
  description  = "IAM policy for logging from a lambda"
  policy = <<EOF
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Action": [
        "logs:CreateLogGroup",
        "logs:CreateLogStream",
        "logs:PutLogEvents"
      ],
      "Resource": [
        "arn:aws:logs:*:*:*"
      ],
      "Effect": "Allow"
    }
  ]
}
EOF
}

resource "aws_iam_policy" "lambda_dynamo" {

  name         = "iam_policy_lambda_function_dynamo"
  path         = "/"
  description  = "IAM policy for dynamodb from a lambda"
  policy = <<EOF
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Action": [
        "dynamodb:BatchGet*",
        "dynamodb:DescribeStream",
        "dynamodb:DescribeTable",
        "dynamodb:Get*",
        "dynamodb:Query",
        "dynamodb:Scan",
        "dynamodb:BatchWrite*",
        "dynamodb:CreateTable",
        "dynamodb:Delete*",
        "dynamodb:Update*",
        "dynamodb:PutItem"
      ],
      "Resource": [
        "${aws_dynamodb_table.request.arn}"
      ],
      "Effect": "Allow"
    }
  ]
}
EOF
}
# Policy Attachment on the role.

resource "aws_iam_role_policy_attachment" "policy_attach_logging" {
  role        = aws_iam_role.lambda_role.name
  policy_arn  = aws_iam_policy.logging.arn
}

resource "aws_iam_role_policy_attachment" "policy_attach_dynamo" {
  role        = aws_iam_role.lambda_role.name
  policy_arn  = aws_iam_policy.lambda_dynamo.arn
}

# Generates an archive from content, a file, or a directory of files.

data "archive_file" "layer_zip" {
  type        = "zip"
  source_dir  = "${path.module}/target/layer"
  output_path = "${path.module}/target/layer.zip"
}


resource "aws_lambda_layer_version" "lambda_layer" {
  filename   = data.archive_file.layer_zip.output_path
  layer_name = "lambda_layer_name"

  compatible_runtimes = ["java11"]
  source_code_hash = data.archive_file.layer_zip.output_base64sha256
}

# Create a lambda function
# In terraform ${path.module} is the current directory.

resource "aws_lambda_function" "lambdafunc" {
  filename                       = "${path.module}/target/triangle_classification.jar"
  function_name                  = "triangle_classification"
  role                           = aws_iam_role.lambda_role.arn
  handler                        = "br.com.sanson.challenge.Handler"
  runtime                        = "java11"
  depends_on                     = [aws_iam_role_policy_attachment.policy_attach_logging,
                                    aws_iam_role_policy_attachment.policy_attach_dynamo]
  layers                         = [aws_lambda_layer_version.lambda_layer.arn]

  source_code_hash               = sha256(filebase64("${path.module}/target/triangle_classification.jar"))
  timeout                        = 25
  memory_size                    = 256
}