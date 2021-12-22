output "base_url" {
  description = "Base URL for API Gateway stage."

  value = aws_api_gateway_deployment.triangle_rest_api_deployment.invoke_url
}