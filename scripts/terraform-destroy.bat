cd ../terraform
terraform destroy -var-file="../secret/terraform.tfvars" -auto-approve
pause
