export interface NotificationReviewCardInterface {
    className?: string | null;
    reviewId: number;
    movieId:number;
    memberId: string;
    nickname: string;
    profileImageId?: number;
    backgroundImageId?: number;
    oneLineReview: string;
    funnyCount: number;
    usefulCount: number;
    dislikeCount: number;
    commentCount: number;
    movieTitle: string;
    releaseYear: number;
    movieGenre: string[];
    createDate: number | Date;
    recommend: boolean;
    notificationId?: number;
    unconfirmedReviewList?: NotificationReviewCardInterface[];
    confirmedReviewList?: NotificationReviewCardInterface[];
    setConfirmedReviewList? : React.Dispatch<React.SetStateAction<NotificationReviewCardInterface[]>>;
    setUnconfirmedReviewList? : React.Dispatch<React.SetStateAction<NotificationReviewCardInterface[]>>;
    confirmed? : boolean;
  }
  