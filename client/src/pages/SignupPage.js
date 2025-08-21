// import React, { useState } from "react";
// import api from "../api/api";

// function SignupPage() {
//   const [email, setEmail] = useState("");
//   const [username, setUsername] = useState("");
//   const [password, setPassword] = useState("");

//   const handleSignup = async () => {
//     try {
//       await api.post("/auth/signup", { email, username, password });
//       alert("회원가입 성공!");
//     } catch (err) {
//       alert(err.response?.data || "회원가입 실패");
//     }
//   };

//   return (
//     <div>
//       <h2>회원가입</h2>
//       <input placeholder="이메일" value={email} onChange={(e) => setEmail(e.target.value)} />
//       <input placeholder="유저이름" value={username} onChange={(e) => setUsername(e.target.value)} />
//       <input type="password" placeholder="비밀번호" value={password} onChange={(e) => setPassword(e.target.value)} />
//       <button onClick={handleSignup}>회원가입</button>
//     </div>
//   );
// }

// export default SignupPage;















import React, { useState } from "react";
const API_URL = "http://localhost:8080/api";

export default function SignupPage() {
  const [email, setEmail] = useState("");
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  const submit = async () => {
    if (!email || !username || !password) return alert("모든 정보를 입력하세요.");
    try {
      const res = await fetch(`${API_URL}/auth/signup`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ email, username, password }),
      });
      if (!res.ok) throw new Error(`Fail: ${res.status}`);
      alert("회원가입 성공");
      setEmail(""); setUsername(""); setPassword("");
    } catch (e) {
      console.error(e);
      alert("회원가입 실패: " + e.message);
    }
  };

  return (
    <div>
      <h2>회원가입</h2>
      <input placeholder="이메일" value={email} onChange={e=>setEmail(e.target.value)} />
      <input placeholder="유저이름" value={username} onChange={e=>setUsername(e.target.value)} />
      <input type="password" placeholder="비밀번호" value={password} onChange={e=>setPassword(e.target.value)} />
      <button onClick={submit}>가입</button>
    </div>
  );
}
