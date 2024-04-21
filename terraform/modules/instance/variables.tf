variable "ssh_key_ids" {
  type = list(string)
}

variable "vultr_plan" {
  type = string
}

variable "vultr_region" {
  type = string
}

variable "vultr_os_id" {
  type = number
}

variable "firewall_group_id" {
  type = string
}

variable "registry_url" {
  type = string
}

variable "registry_username" {
  type = string
}

variable "registry_api_key" {
  type = string
}
