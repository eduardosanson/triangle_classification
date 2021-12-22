resource "aws_iam_role" "apigateway_execution_role" {
  name               = "${var.application_name}-apigateway-role"
  path               = "/"
  description        = "Role assigned to API Gateway"
  assume_role_policy = data.aws_iam_policy_document.apigateway_trust_policy.json
}

resource "aws_iam_policy" "lambda_permissions_policy" {
  name   = "${var.application_name}-lambda-permissions"
  path   = "/"
  policy = data.aws_iam_policy_document.apigateway_lambda_policy.json
}

resource "aws_iam_role_policy_attachment" "lambda_permissions_policy_attach" {
  role       = aws_iam_role.apigateway_execution_role.name
  policy_arn = aws_iam_policy.lambda_permissions_policy.arn
}

resource "aws_iam_role_policy" "api_logging" {
  name         = "iam_policy_api_logging_function"
  role         = aws_iam_role.apigateway_execution_role.id
  policy = <<EOF
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Action": [
        "logs:CreateLogGroup",
        "logs:CreateLogStream",
        "logs:DescribeLogGroups",
        "logs:DescribeLogStreams",
        "logs:PutLogEvents",
        "logs:GetLogEvents",
        "logs:FilterLogEvents"
      ],
      "Resource": [
        "*"
      ],
      "Effect": "Allow"
    }
  ]
}
EOF
}

resource "aws_iam_role_policy" "example_policy" {
  name = "example_policy"
  role = aws_iam_role.apigateway_execution_role.id
  policy = data.aws_iam_policy_document.dynamodb_table_policy.json
}