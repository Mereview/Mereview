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
  // const imageHandler = (file) => {
  //   const input = document.createElement("input");
  //   input.setAttribute("type", "file");
  //   input.setAttribute("accept", "image/*");
  //   input.click();
  //   input.addEventListener("change", async () => {
  //     const file = input.files[0];
  //     const formData = new FormData();
  //     formData.append("image", file);
  //     const fileName = file.name;
  //     console.log(formData.get("image"));
  //     return formData.get("image").slice;
  //   });
  // };
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
      style={{ height: "100%", borderRadius: "5px", border: "black solid" }}
      theme="snow"
      value={content}
      onChange={contentHandler}
      modules={modules}
      formats={formats}
    />
  );
});

export default TextEditor;
