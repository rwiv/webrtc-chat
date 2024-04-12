
const protocol = "https"
const host = "localhost";
const port = 8080;
const domain = `${host}:${port}`;

export const consts = {
  protocol,
  host,
  port,
  domain,
  endpoint: `${protocol}://${domain}`,
}
