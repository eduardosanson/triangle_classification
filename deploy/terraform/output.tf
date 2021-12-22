output "lambda_role_name" {
  value = aws_iam_role.lambda_role.name
}

output "lambda_role_arn" {
  value = aws_iam_role.lambda_role.arn
}

output "aws_iam_policy_lambda_logging_arn" {
  value = aws_iam_policy.logging.arn
}

output "base_url" {
  description = "Base URL for API Gateway stage."

  value = module.api_gw.base_url
}

output "dynamo_arn" {
  value = aws_dynamodb_table.request.arn
}

output "lambda_arn"{
  value = aws_lambda_function.lambdafunc.invoke_arn
}
