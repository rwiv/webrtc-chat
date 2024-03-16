import {useQuery, gql, useMutation} from "@apollo/client";
import {Mutation, Query} from "@/graphql/types.ts";
import {useEffect} from "react";

const chatRooms = gql`
  query ChatRooms {
    chatRooms {
      id
      createAccount {
        id
        username
      }
      title
      password
      createDate
      type
      chatMessages {
        id
      }
      chatUsers {
        id
      }
    }
  }
`;

const createChatRoomQL = gql`
  mutation CreateChatRoom($creation: ChatRoomCreation!) {
    createChatRoom(creation: $creation) {
      id
      title
      createDate
      createAccount {
        id
        username
        nickname
      }
    }
  }
`

const deleteChatRoomQL = gql`
  mutation DeleteChatRoom($chatRoomId: Long!) {
    deleteChatRoom(chatRoomId: $chatRoomId) {
      id
      title
      createDate
      createAccount {
        id
        username
        nickname
      }
    }
  }
`

export default function IndexPage() {
  const {data} = useQuery<Query>(chatRooms);
  const [createChatRoom] = useMutation<Mutation>(createChatRoomQL, {
    refetchQueries: [ "ChatRooms" ],
  });
  const [deleteChatRoom] = useMutation<Mutation>(deleteChatRoomQL, {
    refetchQueries: [ "ChatRooms" ],
  });

  useEffect(() => {
    console.log(data);
  }, [data]);

  const onAddChatRoom = async () => {
    const variables = {
      "creation": {
        "createUserId": 1,
        "password": null,
        "title": "test chatroom",
        "type": "PUBLIC"
      }
    }
    const res = await createChatRoom({variables})
    console.log(res.data?.createChatRoom);
  }

  const onDeleteChatRoom = async (chatRoomId: number) => {
    const variables = {
      chatRoomId,
    }
    const res = await deleteChatRoom({ variables})
    console.log(res.data?.deleteChatRoom);
  }

  return (
    <>
      {data?.chatRooms?.map(chatRoom => (
        <div key={chatRoom.id}>
          <span>{chatRoom.title}  </span>
          <button onClick={() => onDeleteChatRoom(chatRoom.id)}>x</button>
        </div>
      ))}
      <button onClick={onAddChatRoom}>add</button>
    </>
  )
}
