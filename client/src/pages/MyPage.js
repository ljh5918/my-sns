// import React, { useEffect, useState } from "react";
// import axios from "axios";

// const API_URL = "http://localhost:8080/api";

// function MyPage({ token }) {
//   const [posts, setPosts] = useState([]);

//   useEffect(() => {
//     fetchMyPosts();
//   }, [token]);

//   const fetchMyPosts = async () => {
//     if (!token) return;
//     try {
//       const res = await axios.get(`${API_URL}/mypage/posts`, {
//         headers: { Authorization: `Bearer ${token}` },
//       });
//       setPosts(res.data);
//     } catch (err) {
//       console.error("내 게시글 불러오기 실패:", err);
//       alert("내 게시글 불러오기 실패: " + err.response?.status);
//     }
//   };

//   return (
//     <div>
//       <h2>마이페이지</h2>
//       {posts.map((post) => (
//         <div key={post.id} style={{ borderBottom: "1px solid #ccc", marginBottom: 10 }}>
//           <p>{post.content}</p>
//           {/* 추후 수정/삭제 버튼 추가 가능 */}
//         </div>
//       ))}
//     </div>
//   );
// }

// export default MyPage;












import React, { useEffect, useState } from "react";
const API_URL = "http://localhost:8080/api";

export default function MyPage({ token }) {
  const [posts, setPosts] = useState([]);

  // 작성폼
  const [content, setContent] = useState("");
  const [imageUrl, setImageUrl] = useState("");

  // 수정폼
  const [editId, setEditId] = useState(null);
  const [editContent, setEditContent] = useState("");
  const [editImageUrl, setEditImageUrl] = useState("");

  const authHeader = { Authorization: `Bearer ${token}`, "Content-Type": "application/json" };

  const loadMyPosts = async () => {
    try {
      const res = await fetch(`${API_URL}/mypage/posts`, { headers: { Authorization: `Bearer ${token}` } });
      if (!res.ok) throw new Error(`Fail: ${res.status}`);
      const data = await res.json();
      setPosts(data);
    } catch (e) {
      console.error("내 게시글 불러오기 실패:", e.message);
      alert("내 게시글 불러오기 실패: " + e.message);
    }
  };

  useEffect(() => {
    if (token) loadMyPosts();
  }, [token]);

  const createPost = async () => {
    if (!content.trim()) return alert("내용을 입력하세요.");
    try {
      const res = await fetch(`${API_URL}/mypage/posts`, {
        method: "POST",
        headers: authHeader,
        body: JSON.stringify({ content, imageUrl }),
      });
      if (!res.ok) throw new Error(`Fail: ${res.status}`);
      const data = await res.json();
      setPosts([data, ...posts]);
      setContent("");
      setImageUrl("");
    } catch (e) {
      console.error("작성 실패:", e.message);
      alert("작성 실패: " + e.message);
    }
  };

  const startEdit = (p) => {
    setEditId(p.id);
    setEditContent(p.content || "");
    setEditImageUrl(p.imageUrl || "");
  };

  const cancelEdit = () => {
    setEditId(null);
    setEditContent("");
    setEditImageUrl("");
  };

  const saveEdit = async (id) => {
    try {
      const res = await fetch(`${API_URL}/mypage/posts/${id}`, {
        method: "PUT",
        headers: authHeader,
        body: JSON.stringify({ content: editContent, imageUrl: editImageUrl }),
      });
      if (!res.ok) throw new Error(`Fail: ${res.status}`);
      const data = await res.json();
      setPosts(posts.map(p => (p.id === id ? data : p)));
      cancelEdit();
    } catch (e) {
      console.error("수정 실패:", e.message);
      alert("수정 실패: " + e.message);
    }
  };

  const removePost = async (id) => {
    if (!window.confirm("삭제하시겠습니까?")) return;
    try {
      const res = await fetch(`${API_URL}/mypage/posts/${id}`, {
        method: "DELETE",
        headers: { Authorization: `Bearer ${token}` },
      });
      if (!res.ok && res.status !== 204) throw new Error(`Fail: ${res.status}`);
      setPosts(posts.filter(p => p.id !== id));
    } catch (e) {
      console.error("삭제 실패:", e.message);
      alert("삭제 실패: " + e.message);
    }
  };

  return (
    <div>
      <h2>마이페이지 (내 게시글)</h2>

      <div style={{ border: "1px solid #ddd", padding: 12, marginBottom: 16 }}>
        <h3>새 글 작성</h3>
        <input
          placeholder="내용"
          value={content}
          onChange={(e) => setContent(e.target.value)}
          style={{ width: 300, marginRight: 8 }}
        />
        <input
          placeholder="이미지 URL (선택)"
          value={imageUrl}
          onChange={(e) => setImageUrl(e.target.value)}
          style={{ width: 300, marginRight: 8 }}
        />
        <button onClick={createPost}>등록</button>
      </div>

      {posts.map(p => (
        <div key={p.id} style={{ borderBottom: "1px solid #ddd", padding: 8, marginBottom: 8 }}>
          {editId === p.id ? (
            <>
              <input
                value={editContent}
                onChange={(e) => setEditContent(e.target.value)}
                style={{ width: 300, marginRight: 8 }}
              />
              <input
                placeholder="이미지 URL"
                value={editImageUrl}
                onChange={(e) => setEditImageUrl(e.target.value)}
                style={{ width: 300, marginRight: 8 }}
              />
              <button onClick={() => saveEdit(p.id)}>저장</button>
              <button onClick={cancelEdit} style={{ marginLeft: 8 }}>취소</button>
            </>
          ) : (
            <>
              <div>{p.content}</div>
              {p.imageUrl && <img src={p.imageUrl} alt="" style={{ maxWidth: 240 }} />}
              <div style={{ marginTop: 8 }}>
                <button onClick={() => startEdit(p)}>수정</button>
                <button onClick={() => removePost(p.id)} style={{ marginLeft: 8 }}>삭제</button>
              </div>
            </>
          )}
        </div>
      ))}
    </div>
  );
}
