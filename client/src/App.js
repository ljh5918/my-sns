// import { useEffect, useState } from 'react';
// import axios from 'axios';

// function App() {
//   const [msg, setMsg] = useState('');

//   useEffect(() => {
//     axios.get('http://localhost:8080/api/hello')
//       .then(res => setMsg(res.data))
//       .catch(err => console.error(err));
//   }, []);

//   return (
//     <div>
//       <h1>{msg}</h1>
//     </div>
//   );
// }

// export default App;




import React, { useEffect, useState } from 'react';
import axios from 'axios';

function App() {
  const [posts, setPosts] = useState([]);

  useEffect(() => {
    axios.get("http://localhost:8080/api/posts")
      .then(res => setPosts(res.data))
      .catch(console.error);
  }, []);

  return (
    <div>
      <h1>📌 일기 목록</h1>
      <ul>
        {posts.map((post, index) => (
          <li key={index}>
            <strong>{post.title}</strong><br />
            {post.content}
          </li>
        ))}
      </ul>
    </div>
  );
}

export default App;
