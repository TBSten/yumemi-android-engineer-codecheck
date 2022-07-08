
# 課題レポート

## 各リンク

[README.md](https://github.com/TBSten/yumemi-android-engineer-codecheck/blob/main/README.md)

[issue一覧](https://github.com/TBSten/yumemi-android-engineer-codecheck/issues?q=is%3Aissue)

[main](app/src/main/kotlin/jp/co/yumemi/android/code_check/)

[res](app/src/main/res)

[test](app/src/test)

## 課題進捗状況について

以下用意していただいた各issueについての進捗状況になります。

| #   | issue名                                                                                                  | 進捗 | 必須 |
| --- | -------------------------------------------------------------------------------------------------------- | ---- | ---- |
| 1   | [ソースコードの可読性の向上](https://github.com/TBSten/yumemi-android-engineer-codecheck/issues/1)       | 100% | ●    |
| 2   | [ソースコードの安全性の向上](https://github.com/TBSten/yumemi-android-engineer-codecheck/issues/2)       | 100% | ●    |
| 3   | [バグを修正](https://github.com/TBSten/yumemi-android-engineer-codecheck/issues/3)                       | 100% | ●    |
| 4   | [Fat Fragment の回避](https://github.com/TBSten/yumemi-android-engineer-codecheck/issues/4)              | 100% | ●    |
| 5   | [プログラム構造をリファクタリング](https://github.com/TBSten/yumemi-android-engineer-codecheck/issues/5) | 80%  |      |
| 6   | [アーキテクチャを適用](https://github.com/TBSten/yumemi-android-engineer-codecheck/issues/6)             | 90%  |      |
| 7   | [テストを追加](https://github.com/TBSten/yumemi-android-engineer-codecheck/issues/7)                     | 30%  |      |
| 8   | [UIをブラッシュアップ](https://github.com/TBSten/yumemi-android-engineer-codecheck/issues/8)             | 60%  |      |
| 9   | [新機能を追加](https://github.com/TBSten/yumemi-android-engineer-codecheck/issues/9)                     | 10%  |      |

## アピールポイント

### 開発手法について

- Gitを最大限活用して, **各Milestoneごとにブランチ** を切ったり、mainブランチに直接pushするのではなくGithubの **プルリクエスト機能** を用いるなど、チーム開発を意識してソースコードを管理しました。

### ソースコードについて

- [#9 新機能を追加](https://github.com/TBSten/yumemi-android-engineer-codecheck/issues/9)にて同じ単語で検索したときに高速化できるよう、キャッシュの仕組みを導入しました。
- クラスや関数にコメントをつけることはもちろんのこと、一目で使用方法がわからないクラスや関数のコメントには **usage** と使用方法を示しました。
- NullPointerExceptionが起きることがないように **!!演算子** の使用を極力控えました。
- 1つのファイルやクラスなどに責務が集中しすぎないように、 **MVVM** を適用・活用しました。
- [# 8UIをブラッシュアップ](https://github.com/TBSten/yumemi-android-engineer-codecheck/issues/8)に伴って　**Jetpack Compose**　を導入しました。

### 機能について

- エラーが起きうる箇所にはcatch句で例外をキャッチし、 **エラーをユーザに伝える** ようにしました。
- Jetpack Compose の`rememberSaveable`を利用することで画面を傾けてもテキストフィールドの文字や検索結果が消えないよう工夫しています。

---

## 反省点

### 開発手法について

- Gitについて、コミット忘れやブランチの切り替え忘れがあったために1つのコミットに変更を詰め込みすぎてしまいました。これではバージョン管理システムの良さを享受できないので、今後はこまめにコミットする癖をつけるようにしたいです。

### ソースコードについて

- テストについてあまり経験がなくどのようなケースをテストすればいいかわからず、結果としてあまり多く実装できなかったです。今後Androidアプリ開発に限らず、各言語やフレームワークのテストについてもキャッチアップしてきたいです。
- Jetpack Compose導入後において、プレビュー機能をあまり活用できませんでした。ただ、UIコンポーネントを分割するよう意識して作ったのでプレビュー機能を適用しやすくはなっていると思います。

### 機能について

- かねてより「WebViewによるレポジトリの情報を表示する機能」を付け加えたいと思っていたのですが、時間が足りず実装することができませんでした。
- ピン留め機能においてもピン留めしたレポジトリを永続化する処理が時間が足りなかったため実装できず、UIのみ実装しています。

## 感想

より読みやすく拡張しやすいソースコードを目指してリファクタリングしたり機能を付け足したりさせていただいた今回の課題ですが、1週間と短い期間でしたが開発しながら「ここはもっとこうすべきか」と自問自答を繰り返しながら開発させていただきました。一度書いたコードを何度も見直してリファクタリングを加えたり、新しい機能を追加したりする経験はあまりしたことがなかったので自分にとってとても貴重な経験になりました。実装し損なった機能も多かったので今後自分で実装していけたらと思います。

---
---
---

# 株式会社ゆめみ Android エンジニアコードチェック課題

## 概要

本プロジェクトは株式会社ゆめみ（以下弊社）が、弊社に Android エンジニアを希望する方に出す課題のベースプロジェクトです。本課題が与えられた方は、下記の概要を詳しく読んだ上で課題を取り組んでください。

## アプリ仕様

本アプリは GitHub のリポジトリを検索するアプリです。

<img src="docs/app.gif" width="320">

### 環境

- IDE：Android Studio Arctic Fox | 2020.3.1 Patch 1
- Kotlin：1.5.31
- Java：11
- Gradle：7.0.1
- minSdk：23
- targetSdk：31

※ ライブラリの利用はオープンソースのものに限ります。

### 動作

1. 何かしらのキーワードを入力
2. GitHub API（`search/repositories`）でリポジトリを検索し、結果一覧を概要（リポジトリ名）で表示
3. 特定の結果を選択したら、該当リポジトリの詳細（リポジトリ名、オーナーアイコン、プロジェクト言語、Star 数、Watcher 数、Fork 数、Issue 数）を表示

## 課題取り組み方法

Issues を確認した上、本プロジェクトを [**Duplicate** してください](https://help.github.com/en/github/creating-cloning-and-archiving-repositories/duplicating-a-repository)（Fork しないようにしてください。必要ならプライベートリポジトリにしても大丈夫です）。今後のコミットは全てご自身のリポジトリで行ってください。

コードチェックの課題 Issue は全て [`課題`](https://github.com/yumemi-inc/android-engineer-codecheck/milestone/1) Milestone がついており、難易度に応じて Label が [`初級`](https://github.com/yumemi-inc/android-engineer-codecheck/issues?q=is%3Aopen+is%3Aissue+label%3A初級+milestone%3A課題)、[`中級`](https://github.com/yumemi-inc/android-engineer-codecheck/issues?q=is%3Aopen+is%3Aissue+label%3A中級+milestone%3A課題+) と [`ボーナス`](https://github.com/yumemi-inc/android-engineer-codecheck/issues?q=is%3Aopen+is%3Aissue+label%3Aボーナス+milestone%3A課題+) に分けられています。課題の必須／選択は下記の表とします。

|                | 初級  | 中級  | ボーナス |
| -------------: | :---: | :---: | :------: |
| 新卒／未経験者 | 必須  | 選択  |   選択   |
|   中途／経験者 | 必須  | 必須  |   選択   |

課題 Issueをご自身のリポジトリーにコピーするGitHub Actionsをご用意しております。  
[こちらのWorkflow](./.github/workflows/copy-issues.yml)を[手動でトリガーする](https://docs.github.com/ja/actions/managing-workflow-runs/manually-running-a-workflow)ことでコピーできますのでご活用下さい。

課題が完成したら、リポジトリのアドレスを教えてください。

## 参考記事

提出された課題の評価ポイントに関しては、[こちらの記事](https://qiita.com/blendthink/items/aa70b8b3106fb4e3555f)に詳しく書かれてありますので、ぜひご覧ください。
