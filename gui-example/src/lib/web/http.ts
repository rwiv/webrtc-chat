export function post(url: string, body: any) {
  return fetch(url, {
    method: "POST",
    body: JSON.stringify(body),
    headers: {
      "Content-Type": "application/json"
    },
    credentials: "include",
  });
}
