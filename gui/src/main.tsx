import "./globals.css";
import ReactDOM from "react-dom/client";
import {createHashRouter, RouteObject} from "react-router-dom";
import {RouterProvider} from "react-router";
import {ApolloClient, ApolloProvider, HttpLink, InMemoryCache} from "@apollo/client";
import {AccountSelectPage} from "@/dev/pages/AccountSelectPage.tsx";
import {consts} from "@/configures/consts.ts";
import {ChatRoomPage} from "@/pages/ChatRoomPage.tsx";
import {SignupPage} from "@/pages/SignupPage.tsx";
import {LoginPage} from "@/pages/LoginPage.tsx";
import {IndexPage} from "@/pages/IndexPage.tsx";
import {TestPage} from "@/dev/pages/TestPage.tsx";

const routes: RouteObject[] = [
  { path: '/', element: <IndexPage /> },
  { path: '/chat-rooms/:chatRoomId', element: <ChatRoomPage /> },
  { path: '/login', element: <LoginPage /> },
  { path: '/signup', element: <SignupPage /> },
];

if (consts.isDev) {
  routes.push({ path: '/test', element: <TestPage /> });
  routes.push({ path: '/account-select', element: <AccountSelectPage /> });
}

const router = createHashRouter(routes);

const client = new ApolloClient({
  link: new HttpLink({
    uri: `${consts.endpoint}/graphql`,
    credentials: "include",
  }),
  cache: new InMemoryCache(),
});

ReactDOM.createRoot(document.getElementById('root')!).render(
  <ApolloProvider client={client}>
    <RouterProvider router={router} />
  </ApolloProvider>
);
