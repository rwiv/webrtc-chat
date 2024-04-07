import {SubmitHandler, useForm} from "react-hook-form";
import {useNavigate} from "react-router";
import {Form, FormControl, FormField, FormItem, FormLabel, FormMessage} from "@/components/ui/form.tsx";
import {Checkbox} from "@/components/ui/checkbox.tsx";
import {Input} from "@/components/ui/input.tsx";
import {Button} from "@/components/ui/button.tsx";
import {Center, VStack} from "@/lib/style/layouts.tsx";
import {useState} from "react";
import {login, LoginRequest} from "@/client/account.ts";

export function LoginPage() {
  const form = useForm<LoginRequest>({
    defaultValues: {
      username: "",
      password: "",
    }
  });
  const [remember, setRemember] = useState(false);
  const navigate = useNavigate();

  const onSubmit: SubmitHandler<LoginRequest> = async req => {
    await login(req, remember);
    navigate("/");
  }

  return (
    <VStack className="space-y-7 p-8">
      <Header />
      <Form {...form}>
        <Center>
          <form
            onSubmit={form.handleSubmit(onSubmit)}
            className="space-y-4"
          >
            <FormField control={form.control} name="username" render={({ field }) => (
              <FormItem className="w-96">
                <FormLabel>Email</FormLabel>
                <FormControl>
                  <Input placeholder="name@example.com" {...field} />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}/>
            <FormField control={form.control} name="password" render={({ field }) => (
              <FormItem className="w-96">
                <FormLabel>Password</FormLabel>
                <FormControl>
                  <Input type="password" autoComplete="off" {...field} />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}/>
            <div className="flex items-center space-x-2">
              <Checkbox checked={remember} onCheckedChange={() => setRemember(prev => !prev)}/>
              <label
                htmlFor="terms2"
                className="text-sm font-medium leading-none peer-disabled:cursor-not-allowed peer-disabled:opacity-70"
              >Remember Account</label>
            </div>
            <Center>
              <VStack>
                <Button type="submit" className="w-60 mt-4">Login</Button>
                <Button variant="outline" className="w-60 mt-1" onClick={() => navigate("/signup")}>Signup</Button>
              </VStack>
            </Center>
          </form>
        </Center>
      </Form>
    </VStack>
  )
}

function Header() {
  return (
    <div className="flex flex-col space-y-2 text-center">
      <h1 className="text-2xl font-semibold tracking-tight">
        Login
      </h1>
      <p className="text-sm text-muted-foreground">
        Enter your account info
      </p>
    </div>
  )
}
