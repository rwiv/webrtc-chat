import ReactDOM from "react-dom/client";
import {createBrowserRouter} from "react-router-dom";
import IndexPage from "@/pages/IndexPage.tsx";
import {RouterProvider} from "react-router";
import {ApolloClient, ApolloProvider, HttpLink, InMemoryCache} from "@apollo/client";
import {AccountSelectPage} from "@/pages/AccountSelectPage.tsx";
import {consts} from "@/configures/consts.ts";
import {ChatRoomPage} from "@/pages/ChatRoomPage.tsx";
import React from "react";

const router = createBrowserRouter([
  { path: '/', element: <IndexPage /> },
  { path: '/account-select', element: <AccountSelectPage /> },
  { path: '/chat-rooms/:chatRoomId', element: <ChatRoomPage /> },
]);

const client = new ApolloClient({
  link: new HttpLink({
    uri: `${consts.endpoint}/graphql`,
    credentials: "include",
  }),
  cache: new InMemoryCache(),
  // headers: {
  //   Authorization: "admin",
  // }
});

ReactDOM.createRoot(document.getElementById('root')!).render(
  <React.StrictMode>
    <ApolloProvider client={client}>
      <RouterProvider router={router} />
    </ApolloProvider>
  </React.StrictMode>,
);
