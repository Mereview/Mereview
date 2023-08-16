import { NotificationReviewCardInterface } from "./NotificationReviewCardInterface";
export interface NotificationReviewListProps {
    unconfirmedReviewList: NotificationReviewCardInterface[];
    confirmedReviewList: NotificationReviewCardInterface[];
    setConfirmedReviewList : React.Dispatch<React.SetStateAction<NotificationReviewCardInterface[]>>;
    setUnconfirmedReviewList : React.Dispatch<React.SetStateAction<NotificationReviewCardInterface[]>>;
    confirmed : boolean;
}