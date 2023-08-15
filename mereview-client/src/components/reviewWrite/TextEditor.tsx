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
      },
    };
  }, []);
  const getContent = () => {
    return content;
  };
  const setCont = (setValue) => {
    setContent(setValue);
  };
  useImperativeHandle(ref, () => ({
    getContent,
    setCont,
  }));
  const contentHandler = (value) => {
    setContent(value);
  };
  return (
    <ReactQuill
      ref={quillRef}
      className="p-0 mb-3"
      style={{
        border: "gray solid",
        overflow: "hidden",
        height: "50vh",
        borderRadius: "5px",
        backgroundColor: "white",
        boxShadow: "2px 2px 4px rgba(0, 0, 0, 0.2)",
      }}
      placeholder="리뷰 상세정보를 작성하세요"
      theme="snow"
      value={content}
      onChange={contentHandler}
      modules={modules}
      formats={formats}
    />
  );
});

export default TextEditor;
