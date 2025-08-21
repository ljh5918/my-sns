// import React, { useState } from "react";
// import api from "../api/api";

// function LoginPage({ setToken }) {
//   const [email, setEmail] = useState("");
//   const [password, setPassword] = useState("");

//   const handleLogin = async () => {
//     try {
//       const res = await api.post("/auth/login", { email, password });
//       setToken(res.data); // JWT 토큰 저장
//       alert("로그인 성공!");
//     } catch (err) {
//       alert("로그인 실패");
//     }
//   };

//   return (
//     <div>
//       <h2>로그인</h2>
//       <input placeholder="이메일" value={email} onChange={(e) => setEmail(e.target.value)} />
//       <input type="password" placeholder="비밀번호" value={password} onChange={(e) => setPassword(e.target.value)} />
//       <button onClick={handleLogin}>로그인</button>
//     </div>
//   );
// }

// export default LoginPage;










import React, { useState } from "react";

const API_URL = "http://localhost:8080/api";

export default function LoginPage({ setToken }) {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const submit = async () => {
    try {
      const res = await fetch(`${API_URL}/auth/login`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ email, password }),
      });
      if (!res.ok) throw new Error(`Fail: ${res.status}`);
      const token = await res.text(); // 또는 await res.json() 후 token 필드
      setToken(token);
      alert("로그인 성공");
    } catch (e) {
      console.error(e);
      alert("로그인 실패: " + e.message);
    }
  };

  return (
    <div>
      <h2>로그인</h2>
      <input placeholder="이메일" value={email} onChange={e=>setEmail(e.target.value)} />
      <input type="password" placeholder="비밀번호" value={password} onChange={e=>setPassword(e.target.value)} />
      <button onClick={submit}>로그인</button>
    </div>
  );
}
