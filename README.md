<h1>StreamingwhileDownloading</h1>

<p>This project downloads the video while streaming it simultaneously.

The project is mainly based upon the files of Sachin Chandil that are available on github at 
<a href = "https://github.com/chandilsachin/VideoDownloadAndPlay">VideoDownloadAndPlay</a> with minor changes so as to
not re-download the videos when the app is opened again, which I have implemented.

For some reason or another when streaming the video from url in <b>surface view</b>, the download is happening but the video is not streaming 
simultaneously, whereas the same works fine with <b>Video View</b>


<h2>How can we check if download is happening??</h2>
<ul>
<li>Open your internal storage and go to</li>
<li>InternalStorage/Android/data/com.example.aditya.downloadwhilestreaming/files </li>

<h2>WORKING</h2>
<p> Open the app with your internet connection <b>ON</b> and click on Download and play.

After 2 seconds or so if your connection is fast, you see the video on the screen.

Press download and play again, this time you see another instance of video from the start.<b>This is the the downloaded video</b>

You may check into your phone that the video has downloaded.

Open the app again, and press the button again.

<b>This time you only see 1 instance of the video and it starts immediately without any delay. Now, the video is being played from the directory or storage.</b>

</p>

<h2>In Some conditions, while running the app, it may crash or say Can't play video. In that case simply close the app, delete any downloaded files that are there in the app directory specified above and start the app again</h2>
<h4>There are some bugs but I am still working on it</h4>
