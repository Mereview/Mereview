import axios from "axios";
import React, {
  useState,
  useRef,
  forwardRef,
  useImperativeHandle,
  useMemo,
} from "react";
import ReactQuill from "react-quill";
import "react-quill/dist/quill.snow.css";

const TextEditor = forwardRef((props, ref) => {
  const [content, setContent] = useState<string>(null);
  const toolbarOptions = [["image"]];
  const formats = ["image"];
  const quillRef = useRef(null);
  const imageHandler = () => {
    console.log("에디터에서 이미지 버튼을 클릭하면 이 핸들러가 시작됩니다!");

    // 1. 이미지를 저장할 input type=file DOM을 만든다.
    const input = document.createElement("input");
    // 속성 써주기
    input.setAttribute("type", "file");
    input.setAttribute("accept", "image/*");
    input.click(); // 에디터 이미지버튼을 클릭하면 이 input이 클릭된다.
  };
  const modules = useMemo(() => {
    return {
      toolbar: {
        container: toolbarOptions,
        // handlers: {
        //   image: imageHandler,
        // },
      },
    };
  }, []);
  const getContent = () => {
    return content;
  };
  useImperativeHandle(ref, () => ({
    getContent,
  }));
  const contentHandler = (value) => {
    setContent(value);
  };
  return (
    <ReactQuill
      ref={quillRef}
      style={{
        overflow: "hidden",
        height: "100%",
        borderRadius: "5px",
        border: "black solid",
      }}
      theme="snow"
      value={content}
      onChange={contentHandler}
      modules={modules}
      formats={formats}
    />
  );
});

export default TextEditor;
