// import React, { useEffect, useState } from "react";

// function MainPage({ token }) {
//   const [posts, setPosts] = useState([]);
//   const [showForm, setShowForm] = useState(false);
//   const [newPost, setNewPost] = useState({ content: "", imageUrl: "" });

//   // 게시글 불러오기
//   useEffect(() => {
//     fetch("http://localhost:8080/api/posts")
//       .then((res) => res.json())
//       .then((data) => setPosts(data))
//       .catch((err) => console.error("게시글 불러오기 실패:", err));
//   }, []);

//   // 새 글 작성
//   const handleSubmit = (e) => {
//     e.preventDefault();

//     if (!token) {
//       alert("로그인 후 글쓰기가 가능합니다.");
//       return;
//     }

//     fetch("http://localhost:8080/api/posts", {
//       method: "POST",
//       headers: {
//         "Content-Type": "application/json",
//         Authorization: `Bearer ${token}`,
//       },
//       body: JSON.stringify(newPost),
//     })
//       .then((res) => {
//         if (!res.ok) throw new Error("글쓰기 실패");
//         return res.json();
//       })
//       .then((data) => {
//         setPosts([data, ...posts]); // 새 글을 맨 위에 추가
//         setNewPost({ content: "", imageUrl: "" }); // 폼 초기화
//         setShowForm(false); // 글쓰기 창 닫기
//       })
//       .catch((err) => alert(err.message));
//   };

//   return (
//     <div>
//       <h2>전체 게시글</h2>

//       {/* 글쓰기 버튼 */}
//       {token && (
//         <button onClick={() => setShowForm(!showForm)}>
//           {showForm ? "취소" : "글쓰기"}
//         </button>
//       )}

//       {/* 글쓰기 폼 */}
//       {showForm && (
//         <form onSubmit={handleSubmit} style={{ marginTop: 20 }}>
//           <textarea
//             placeholder="내용을 입력하세요"
//             value={newPost.content}
//             onChange={(e) => setNewPost({ ...newPost, content: e.target.value })}
//             required
//             style={{ width: "100%", height: "80px" }}
//           />
//           <input
//             type="text"
//             placeholder="이미지 URL (선택)"
//             value={newPost.imageUrl}
//             onChange={(e) => setNewPost({ ...newPost, imageUrl: e.target.value })}
//             style={{ width: "100%", marginTop: 5 }}
//           />
//           <button type="submit" style={{ marginTop: 10 }}>
//             등록
//           </button>
//         </form>
//       )}

//       {/* 게시글 목록 */}
//       <div style={{ marginTop: 20 }}>
//         {posts.map((post) => (
//           <div
//             key={post.id}
//             style={{ border: "1px solid #ddd", margin: "10px", padding: "10px" }}
//           >
//             <p>{post.content}</p>
//             {post.imageUrl && <img src={post.imageUrl} alt="게시글 이미지" width="200" />}
//             <small>작성자: {post.authorUsername}</small>
//           </div>
//         ))}
//       </div>
//     </div>
//   );
// }

// export default MainPage;
























import React, { useEffect, useState } from "react";
const API_URL = "http://localhost:8080/api";

export default function MainPage() {
  const [posts, setPosts] = useState([]);

  useEffect(() => {
    const run = async () => {
      try {
        const res = await fetch(`${API_URL}/posts`);
        if (!res.ok) throw new Error(`Fail: ${res.status}`);
        const data = await res.json();
        setPosts(data);
      } catch (e) {
        console.error("게시글 불러오기 실패:", e.message);
      }
    };
    run();
  }, []);

  return (
    <div>
      <h2>전체 게시글</h2>
      {posts.map(p => (
        <div key={p.id} style={{ borderBottom: "1px solid #ddd", padding: 8, marginBottom: 8 }}>
          <div>{p.content}</div>
          {p.imageUrl && (
            <div>
              <img src={p.imageUrl} alt="" style={{ maxWidth: 240 }} />
            </div>
          )}
          <small>
            by {p.authorUsername} ({p.authorEmail})
          </small>
        </div>
      ))}
      <p style={{ color: "#666" }}>※ 메인페이지에서는 읽기만 가능합니다.</p>
    </div>
  );
}

