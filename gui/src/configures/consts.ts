import {env} from "@/configures/env.ts";


const isDev = env.MODE === "development";
const isProd = env.MODE === "production";
const isStage = env.MODE === "stage";

const protocol = env.VITE_PROTOCOL;
const host = env.VITE_HOST
const port = env.VITE_PORT
const domain = isDev ? `${host}:${port}` : window.location.host;

const apiPrefix = isDev ? "" : "/api";
const endpoint = `${protocol}://${domain}${apiPrefix}`;

export const consts = {
  isDev,
  isProd,
  isStage,
  protocol,
  host,
  port,
  domain,
  endpoint,
  apiPrefix,
}
