import React from "react";
import ReactDOM from "react-dom/client";
import {createBrowserRouter} from "react-router-dom";
import IndexPage from "@/pages/IndexPage.tsx";
import {RouterProvider} from "react-router";
import {ApolloClient, ApolloProvider, InMemoryCache} from "@apollo/client";

const router = createBrowserRouter([
  { path: '/', element: <IndexPage /> },
]);

const client = new ApolloClient({
  uri: "http://localhost:8080/graphql",
  cache: new InMemoryCache(),
  headers: {
    Authorization: "admin",
  }
});

ReactDOM.createRoot(document.getElementById('root')!).render(
  <React.StrictMode>
    <ApolloProvider client={client}>
      <RouterProvider router={router} />
    </ApolloProvider>
  </React.StrictMode>,
);
