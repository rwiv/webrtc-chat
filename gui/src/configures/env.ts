interface Meta {
  env: Env;
}

export interface Env {
  BASE_URL: string;
  MODE: string;
  DEV: boolean;
  PROD: boolean;
  SSR: boolean;

  VITE_PROTOCOL: string;
  VITE_HOST: string;
  VITE_PORT: string;
}

export const env = (import.meta as unknown as Meta).env
