terraform {
  required_providers {
    vultr = {
      source = "vultr/vultr"
      version = "2.18.0"
    }
  }
}

provider "vultr" {
  api_key = var.vultr_api_key
  rate_limit = 100
  retry_limit = 3
}

resource "vultr_ssh_key" "chat_ssh_key" {
  name = "chat-ssh-key"
  ssh_key = file("../secret/ssh-keygen.pub")
}

module "firewall" {
  source = "./modules/firewall"
}

module "instance" {
  source = "./modules/instance"
  vultr_plan = var.vultr_plan
  vultr_region = var.vultr_region
  vultr_os_id = var.vultr_os_id
  ssh_key_ids = [vultr_ssh_key.chat_ssh_key.id]
  firewall_group_id = module.firewall.group_id
  registry_url = var.registry_url
  registry_username = var.registry_username
  registry_api_key = var.registry_api_key
}
