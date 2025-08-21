import React from "react";

function PostItem({ post, token, onEdit, onDelete }) {
  return (
    <div style={{ borderBottom: "1px solid #ccc", marginBottom: 10, padding: 8 }}>
      <p>{post.content}</p>
      {token && (
        <>
          <button onClick={() => onEdit(post)}>✏ 수정</button>
          <button onClick={() => onDelete(post.id)}>🗑 삭제</button>
        </>
      )}
    </div>
  );
}

export default PostItem;
