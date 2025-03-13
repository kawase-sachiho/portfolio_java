# フィットネスアプリ

運動時間・内容をカテゴリーごとに記録し、履歴を確認できるアプリケーション。

![ログイン画面](image/login.png)
ログイン画面。メールアドレスとパスワードでログインする。

![トップ画面](image/top.png)
TOP画面。運動記録画面と運動履歴画面を選択できる。

![運動記録の選択画面](image/select.png)
運動記録の選択画面。3つのカテゴリーから選択できる。

![運動記録一覧画面](image/exercise.png)
運動記録の一覧画面。ボタンから、新規作成、詳細表示、記事編集の画面に移動できる。
削除を選択すると、ダイアログボックスで確認アラートが表示される。

![運動記録の作成画面](image/exercise_create.png)
運動記録の作成画面。

![運動記録の作成画面](image/exercise_confirm.png)
運動記録の詳細表示画面。

![運動記録の編集画面](image/exercise_update.png)
運動記録の編集画面。初期値として元の値が表示される。

![運動履歴の一覧画面](image/history.png)
運動履歴の一覧画面。過去30日分の運動履歴のデータを表示する。

![ユーザー新規作成画面](image/user_create.png)
ユーザーの新規作成画面。

![ユーザー情報編集画面](image/user_update.png)
ユーザー情報の編集画面。初期値として元の値が表示される。

![エラー画面](image/error.png)
エラー画面。エラー発生時に表示される。

※各入力画面は、バリデーションのエラーがあると入力値を保持したまま入力画面に戻る。

## 概要

  - 運動記録を作成することで、運動への意識を高める。
  - 運動履歴を確認することで、運動習慣を把握する。

## 開発した背景

私は、コロナ禍で外出する頻度が減り、体重が増加しました。
運動をして減量を試みましたが、運動の内容や時間が定まらず、長続きしませんでした。
そこで、運動した時間や内容を記録するアプリケーションがあれば、計画的に運動を行えると考え、作成に至りました。

## 使用技術

  - Java 21.0.6 JSP/サーブレット
  - H2

## 機能一覧

  - ユーザー登録、ログイン機能
    - ユーザー情報編集機能
  - 運動記録機能
    - 運動記録の作成・修正・削除機能
    - 運動記録の詳細表示機能
  - 運動履歴機能
    - 運動時間の一覧を表示する機能
    - 運動時間の合計を表示する機能

## 苦労した点

  - カテゴリーごとで別のレコードにわかれている運動時間を、1つの配列として抽出する為のSQL文の作成(dao/ExerciseDAO/findAllExecercisesメソッド)
  - 運動時間を合算し、分単位から時間単位へ変換するメソッドの作成(logic/ConvertTimeLogic/getTimeSumメソッド)
