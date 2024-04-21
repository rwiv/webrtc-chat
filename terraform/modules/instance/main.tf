resource "vultr_instance" "my_instance" {
  plan = var.vultr_plan
  region = var.vultr_region
  os_id = var.vultr_os_id
  ssh_key_ids = var.ssh_key_ids
  firewall_group_id = var.firewall_group_id

  provisioner "local-exec" {
    command = "echo ${self.main_ip} > ip.txt"
  }

  connection {
    type        = "ssh"
    user        = "root"
    private_key = file("../secret/ssh-keygen")
    host        = self.main_ip
  }

  provisioner "file" {
    source      = "./scripts/init.sh"
    destination = "/root/init.sh"
  }

  provisioner "file" {
    source      = "../docker/docker-compose-prod-temp.yml"
    destination = "/root/docker-compose-prod-temp.yml"
  }

  provisioner "file" {
    source      = "../secret/.env"
    destination = "/root/.env"
  }

  provisioner "remote-exec" {
    inline = [
      "chmod +x /root/init.sh",
      "/root/init.sh ${var.registry_url} ${var.registry_username} ${var.registry_api_key}",
      "docker compose -f /root/docker-compose-prod-temp.yml --env-file /root/.env up -d"
    ]
  }
}
