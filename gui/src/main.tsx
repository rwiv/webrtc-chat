import ReactDOM from "react-dom/client";
import {createHashRouter} from "react-router-dom";
import {RouterProvider} from "react-router";
import {ApolloClient, ApolloProvider, HttpLink, InMemoryCache} from "@apollo/client";
import {AccountSelectPage} from "@/pages/AccountSelectPage.tsx";
import {consts} from "@/configures/consts.ts";
import {ChatRoomPage} from "@/pages/ChatRoomPage.tsx";
import "./globals.css";
import {SignupPage} from "@/pages/SignupPage.tsx";
import {LoginPage} from "@/pages/LoginPage.tsx";
import IndexPage from "@/pages/IndexPage.tsx";
import {TestPage} from "@/pages/TestPage.tsx";

const router = createHashRouter([
  { path: '/', element: <IndexPage /> },
  { path: '/test', element: <TestPage /> },
  { path: '/account-select', element: <AccountSelectPage /> },
  { path: '/chat-rooms/:chatRoomId', element: <ChatRoomPage /> },
  { path: '/login', element: <LoginPage /> },
  { path: '/signup', element: <SignupPage /> },
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
  <ApolloProvider client={client}>
    <RouterProvider router={router} />
  </ApolloProvider>
);
