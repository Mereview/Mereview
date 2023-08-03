import React, { useState } from "react";
import ReactQuill from "react-quill";
import "react-quill/dist/quill.snow.css";

const TextEditor = () => {
  const [text, setText] = useState<string>(null);
  const textHandler = (e) => {
    setText(e.target.value);
  };
  const CustomToolbar = () => (
    <div id="toolbar">
      <button className="ql-link"></button>
      <button className="ql-image"></button>
    </div>
  );
  const modules = {
    toolbar: {
      container: "#toolbar",
    },
  };

  const formats = [
    "header",
    "font",
    "size",
    "bold",
    "italic",
    "underline",
    "list",
    "bullet",
    "align",
    "color",
    "background",
    "image",
  ];
  return (
    <div>
      <div className="text-editor">
        <CustomToolbar />
        <ReactQuill
          modules={modules}
          formats={formats}
          value={text}
          onChange={textHandler}
        />
      </div>
    </div>
  );
};

export default TextEditor;
