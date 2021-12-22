terraform {
  backend "s3" {
    bucket  = "applications-terraform.state"
    region  = "us-east-1"
    key     = "triangle-challenge/terraform.tfstate"
    profile = "personal-account"
  }
}


provider "aws" {
  region = var.region
}

module "api_gw" {
  source                 = "./module/api_gw"
  application_name       = var.application_name
  region                 = var.region
  dynamodb_table_name    = aws_dynamodb_table.request.name
  dynamodb_table_arn     = aws_dynamodb_table.request.arn
  lambda_func_arn        = aws_lambda_function.lambdafunc.arn
  lambda_func_invoke_url = aws_lambda_function.lambdafunc.invoke_arn
}