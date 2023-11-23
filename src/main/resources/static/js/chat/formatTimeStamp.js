function formatTimestamp(timestamp) {
    const messageDate = new Date(timestamp);
    const now = new Date();

    const isToday = now.toDateString() === messageDate.toDateString();
    const isYesterday = new Date(now.setDate(now.getDate() - 1)).toDateString() === messageDate.toDateString();
    const isThisYear = now.getFullYear() === messageDate.getFullYear();

    const timeString = messageDate.toLocaleTimeString('ko-KR', {
        hour: '2-digit',
        minute: '2-digit',
        hour12: true
    }).toUpperCase();

    if (isToday) {
        // 오늘 보낸 메시지
        return timeString;
    } else if (isYesterday) {
        // 어제 보낸 메시지
        return `어제 ${timeString}`;
    } else if (isThisYear) {
        // 올해 이전에 보낸 메시지
        return `${messageDate.getMonth() + 1}.${messageDate.getDate()} ${timeString}`;
    } else {
        // 작년 이전에 보낸 메시지
        return `${messageDate.getFullYear().toString().slice(2)}.${messageDate.getMonth() + 1}.${messageDate.getDate()} ${timeString}`;
    }
}