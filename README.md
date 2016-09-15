# CreditSesameRingView

[![](https://jitpack.io/v/HotBitmapGG/CreditSesameRingView.svg)](https://jitpack.io/#HotBitmapGG/CreditSesameRingView) [![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-CreditSesameRingView-brightgreen.svg?style=flat)](http://android-arsenal.com/details/1/4327) [![Wercker](https://img.shields.io/badge/Gradle-2.1.3-brightgreen.svg)]() [![Wercker](https://img.shields.io/wercker/ci/wercker/docs.svg?maxAge=2592000?style=plastic)]() [![Hex.pm](https://img.shields.io/hexpm/l/plug.svg?maxAge=2592000?style=plastic)]()

Imitation of Ali credit sesame new old ring view,If you feel like it, give me a star, please support, thank you.

## Screenshots Gif

<a href="art/01.gif"><img src="art/01.gif" width="40%"/></a> <a href="art/02.gif"><img src="art/02.gif" width="40%"/></a>

## Screenshots Png
<a href="art/03.png"><img src="art/03.png" width="40%"/></a> <a href="art/04.png"><img src="art/04.png" width="40%"/></a>



## Usage

 * add dependencies

```java

Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url "https://jitpack.io" }
		}
	}



Add the dependency

	dependencies {
	        compile 'com.github.HotBitmapGG:CreditSesameRingView:V1.0.3'
	}

```

 * Sted 1 A new version of used in the code.

```java

     // The gradient color can define your own
     private final int[] mColors = new int[]{
            0xFFFF80AB,
            0xFFFF4081,
            0xFFFF5177,
            0xFFFF7997
             };

      // Set up the need to score
      mLayout = (RelativeLayout) view.findViewById(R.id.layout);
      mButton = (Button) view.findViewById(R.id.btn);
      newCreditSesameView = (NewCreditSesameView) view.findViewById(R.id.sesame_view);
      mLayout.setBackgroundColor(mColors[0]);
      mButton.setOnClickListener(new View.OnClickListener()
      {

          @Override
           public void onClick(View view)
           {

               newCreditSesameView.setSesameValues(639);
               startColorChangeAnim();
           }
      });


     // The background color gradient animation Simply illustrates the effect Can customize according to your need
     public void startColorChangeAnim()
     {

         ObjectAnimator animator = ObjectAnimator.ofInt(mLayout, "backgroundColor", mColors);
         animator.setDuration(3000);
         animator.setEvaluator(new ArgbEvaluator());
         animator.start();
     }

```

  * Sted 2 A old version of used in the code.

```java

      // Set up the need to score
      oldCreditSesameView = (OldCreditSesameView) view.findViewById(R.id.sesame_view);
      mButton = (Button) view.findViewById(R.id.btn);
      mButton.setOnClickListener(new View.OnClickListener()
      {

          @Override
          public void onClick(View view)
          {

              oldCreditSesameView.setSesameValues(639);
          }
      });
```

## Other

  * 高仿BiliBili客户端: https://github.com/HotBitmapGG/OhMyBiliBili

  * 知了日报客户端: https://github.com/HotBitmapGG/RxZhiHu

  * Gank.io客户端: https://github.com/HotBitmapGG/StudyProject

  * 妹子福利App: https://github.com/HotBitmapGG/MoeQuest

  * 圆环进度条:https://github.com/HotBitmapGG/RingProgressBar

  * 仿芝麻信用分仪表盘: https://github.com/HotBitmapGG/CreditSesameRingView

## License

   Copyright 2016 HotBitmapGG

   Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.




