import {env} from "@/configures/env.ts";

const protocol = env.VITE_PROTOCOL;
const host = env.VITE_HOST
const port = env.VITE_PORT
const domain = `${host}:${port}`;
const endpoint = `${protocol}://${domain}`;

export const consts = {
  isDev: env.MODE === "development",
  isProd: env.MODE === "production",
  protocol,
  host,
  port,
  domain,
  endpoint,
}
