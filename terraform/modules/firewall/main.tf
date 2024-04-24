resource "vultr_firewall_group" "chat_firewall_group" {
  description = "chat-firewall-group"
}

resource "vultr_firewall_rule" "ssh_firewall_rule" {
  firewall_group_id = vultr_firewall_group.chat_firewall_group.id
  protocol = "tcp"
  ip_type = "v4"
  subnet = "0.0.0.0"
  subnet_size = 0
  port = "22"
}

resource "vultr_firewall_rule" "http_firewall_rule" {
  firewall_group_id = vultr_firewall_group.chat_firewall_group.id
  protocol = "tcp"
  ip_type = "v4"
  subnet = "0.0.0.0"
  subnet_size = 0
  port = "80"
}

resource "vultr_firewall_rule" "https_firewall_rule" {
  firewall_group_id = vultr_firewall_group.chat_firewall_group.id
  protocol = "tcp"
  ip_type = "v4"
  subnet = "0.0.0.0"
  subnet_size = 0
  port = "443"
}
