init-prod: clean
	cd deploy/terraform; terraform init ; \
	terraform workspace new prod || terraform workspace select prod

prod-workspace:
	cd deploy/terraform; terraform workspace new prod || terraform workspace select prod

apply-prod: init-prod prod-workspace apply

apply:
	cd deploy/terraform; terraform apply

plan:
	cd deploy/terraform; terraform plan

clean:
	cd deploy/terraform; rm -rf .terraform

destroy:
	cd deploy/terraform; terraform destroy

validate:
	cd deploy/terraform; terraform init -backend=false && terraform validate

build-deploy:
	mvn clean install -P deploy