resource "aws_dynamodb_table" "request" {
  name             = "request"
  hash_key         = "requestKey"
  billing_mode     = "PAY_PER_REQUEST"

  attribute {
    name = "requestKey"
    type = "S"
  }
}
