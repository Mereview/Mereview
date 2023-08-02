import { useEffect, useRef } from "react";
import "../styles/css/ReviewDetailPage.css";
import Top from "../components/reviewDetail/Top";
import TopOfDetail from "../components/reviewDetail/TopOfDetail";
import BottomOfDetail from "../components/reviewDetail/BottomOfDetail";

export interface DummyRev {
  title: string;
  oneLine: string;
  content: string;
  keyword: { [key: string]: string }; // 키와 값의 타입을 모두 문자열로 정의
}
export interface DummyMov {
  title: string;
  released_date: string;
  genres: string[];
}
const ReviewDetail = (props: any) => {
  const containerRef = useRef(null);

  const handleScroll = () => {
    const container = containerRef.current;
    if (!container) return;

    const sections = container.getElementsByClassName("section");
    for (let i = 0; i < sections.length; i++) {
      const section = sections[i];
      const rect = section.getBoundingClientRect();
      if (
        rect.top <= window.innerHeight * 0.5 &&
        rect.bottom >= window.innerHeight * 0.5
      ) {
        const offsetTop = section.classList.contains("section1")
          ? section.offsetTop - 74 // 첫 번째 단락에 네비바 높이만큼 보정
          : section.offsetTop;

        const currentScroll = window.scrollY;
        const scrollAmount = offsetTop - currentScroll;
        window.scrollBy({ top: scrollAmount, behavior: "smooth" });
        break;
      }
    }
  };

  useEffect(() => {
    window.addEventListener("scroll", handleScroll);
    return () => window.removeEventListener("scroll", handleScroll);
  }, []);
  const imgURL = "/test.jpg";
  const style = { backgroundImage: `url(${imgURL})` };
  const dummyReview: DummyRev = {
    title: "믿고 보는 톰크루즈",
    oneLine: "볼 수 밖에 없다",
    content:
      "꼬마붕붕 카체이싱에 절벽 낙하, 열차 격투까지 액션의, 클레멘티에프의 활약도 눈부시고, 이 난리 끝에 스르륵 등장하는 사이먼 페그는 살짝 얄밉기도. 좀 길고, 자잘한 설명이 많은 게 아쉽다만 그래도 톰 크루즈의 장인 정신은 또 2편을 기대하게 한다. 쾌감과 스릴의 극단. 거기에 헤일리 앳웰",
    keyword: {
      액션: "5",
      연출: "5",
      스토리: "3",
      톰크루즈: "5",
      노잼: "1",
    },
  };
  const dummyMovie: DummyMov = {
    title: "미션 임파서블: 데드레코닝 PART ONE",
    released_date: "2023-07-12",
    genres: ["액션", "모험", "스릴러"],
  };

  return (
    <div className="review-detail-container" ref={containerRef} style={style}>
      <div className="section section1">
        <Top review={dummyReview} movie={dummyMovie} />
        <TopOfDetail review={dummyReview} movie={dummyMovie} />
      </div>
      <div className="section">
        <BottomOfDetail />
      </div>
    </div>
  );
};

export default ReviewDetail;
