#News Gateway App
##Introduction
News Gateway is a comprehensive news application designed to deliver the most current news articles from a wide variety of news sources covering a range of categories. This application leverages the robust capabilities of NewsAPI.org to acquire news sources and articles.

##Highlights
1.News Gateway provides an option to select from various news sources, displaying up to 10 top stories from the chosen source.
2.The app includes a category filter, limiting the news source choices to those specific to the selected category.
3.The application allows for intuitive navigation with right swipes to read the next article and left swipes to go back to the previous article.
4.Clicking on the article title, text, or image content redirects the user to the complete extended article on the news source’s website.
5.The app features a professional-looking launcher icon.

##Data Sources
News source and article data are acquired through the NewsAPI.org news aggregation service. This service allows downloading of news sources (all or by news type/category) and news articles (by news source).

##API Usage
NewsAPI.org provides two API calls - one to get news sources (like CNN, Time, etc.) and another to get top news articles from a selected news source.

##Development
News Gateway utilizes a range of Android technologies including Services, Broadcasts & Receivers, Drawer Layout, Fragments, ViewPager, and Internet APIs.
