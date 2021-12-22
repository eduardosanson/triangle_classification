resource "aws_api_gateway_rest_api" "triangle_rest_api" {
  name       = var.application_name
  endpoint_configuration {
    types = ["REGIONAL"]
  }
  body = data.template_file.api_file.rendered
}

resource "aws_api_gateway_deployment" "triangle_rest_api_deployment" {
  rest_api_id = aws_api_gateway_rest_api.triangle_rest_api.id
  stage_name = "v1"
  triggers = {
    # NOTE: The configuration below will satisfy ordering considerations,
    #       but not pick up all future REST API changes. More advanced patterns
    #       are possible, such as using the filesha1() function against the
    #       Terraform configuration file(s) or removing the .id references to
    #       calculate a hash against whole resources. Be aware that using whole
    #       resources will show a difference after the initial implementation.
    #       It will stabilize to only change when resources change afterwards.
    redeployment = sha1(jsonencode([
      data.template_file.api_file.template,
    ]))
  }
}


resource "aws_api_gateway_usage_plan" "usage_plan" {
  name        = "${var.application_name}-usage-plan"
  description = "Usage plan for Triangle"

  api_stages {
    api_id = aws_api_gateway_rest_api.triangle_rest_api.id
    stage  = aws_api_gateway_deployment.triangle_rest_api_deployment.stage_name
  }

  throttle_settings {
    burst_limit = 500
    rate_limit  = 100
  }

}

resource "aws_api_gateway_api_key" "api_key" {
  name        = "${var.application_name}-key"
  description = "API Key for Triangle classify API"
}

# ----------------------------------------------------------------------
# API Key - Usage Plan Mapping
# ----------------------------------------------------------------------
resource "aws_api_gateway_usage_plan_key" "usage_plan_key" {
  depends_on = [aws_api_gateway_deployment.triangle_rest_api_deployment]
  key_id        = aws_api_gateway_api_key.api_key.id
  key_type      = "API_KEY"
  usage_plan_id = aws_api_gateway_usage_plan.usage_plan.id
}


resource "aws_api_gateway_method_settings" "example" {
  rest_api_id = aws_api_gateway_rest_api.triangle_rest_api.id
  stage_name  = aws_api_gateway_deployment.triangle_rest_api_deployment.stage_name
  method_path = "*/*"


  settings {
    metrics_enabled = true
    logging_level   = "INFO"
  }

}