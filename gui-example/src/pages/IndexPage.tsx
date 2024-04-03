import "@/css/IndexPage.css";
import {LeftSideBar} from "@/components/layouts/LeftSideBar.tsx";
import {ChatRoomContent} from "@/components/layouts/ChatRoomContent.tsx";

export default function IndexPage() {

  return (
    <div className="container">
      <LeftSideBar />
      <ChatRoomContent chatRoomId={null} />
    </div>
  );
}
