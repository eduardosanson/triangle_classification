data "aws_iam_policy_document" "apigateway_trust_policy" {
  statement {
    effect = "Allow"

    principals {
      type        = "Service"
      identifiers = ["apigateway.amazonaws.com"]
    }

    actions = ["sts:AssumeRole"]
  }
}

data "aws_iam_policy_document" "apigateway_lambda_policy" {
  statement {
    effect    = "Allow"
    actions   = ["lambda:InvokeFunction"]
    resources = [var.lambda_func_arn]
  }
}
data "template_file" "response_template" {
  template = <<EOF
      #set($inputRoot = $input.path('$'))      \n \
      \ [                                      \n \
      \   #foreach($elem in $inputRoot.Items){ \n \
      \   \"requestKey\": \"$elem.requestKey.S\",  \n \
      \   }#if($foreach.hasNext),#end          \n \
      \ #end                                   \n \
      \ ]
    EOF
}
data "template_file" "api_file" {
  template = file("${path.module}/files/api.yml")

  vars = {
    app_name                   = var.application_name,
    api_gateway_execution_role = aws_iam_role.apigateway_execution_role.arn
    lambda_invoke_arn          = var.lambda_func_invoke_url
    dynamo_action              = "arn:aws:apigateway:${var.region}:dynamodb:action/Scan"
    table_name                 = var.dynamodb_table_name
    dynamo_response_template   = data.template_file.response_template.rendered
  }
}

# The policy document to access the role
data "aws_iam_policy_document" "dynamodb_table_policy" {
  statement {
    sid = "dynamodbtablepolicy"

    actions = [
      "dynamodb:Query",
      "dynamodb:Get*",
      "dynamodb:Scan"
    ]

    resources = [
      var.dynamodb_table_arn,
    ]
  }
}