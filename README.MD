参考 https://github.com/Jhuster/AudioDemo

Android SDK 提供了3套音频播放的API，分别是：MediaPlayer，SoundPool，AudioTrack

MediaPlayer 更加适合在后台长时间播放本地音乐文件或者在线的流式资源; 
SoundPool 则适合播放比较短的音频片段，比如游戏声音、按键声、铃声片段等等，它可以同时播放多个音频; 
AudioTrack 则更接近底层，提供了非常强大的控制能力，支持低延迟播放，适合流媒体和VoIP语音电话等场景。

PCM 脉冲编码调制(Pulse Code Modulation,PCM)
