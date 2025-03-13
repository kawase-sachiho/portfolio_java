# フィットネスアプリ

運動時間・内容をカテゴリーごとに記録し、履歴を確認できるアプリケーション。

## 概要

  - 運動記録を作成することで、運動への意識を高める。
  - 運動履歴を確認することで、運動習慣を把握する。

## 開発した背景

私は、コロナ禍で外出する頻度が減り、体重が増加しました。
運動をして減量を試みましたが、運動の内容や時間が定まらず、長続きしませんでした。
そこで、運動した時間や内容を記録するアプリケーションがあれば、計画的に運動を行えると考え、作成に至りました。

## 使用技術

  - Java
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

  - カテゴリーごとで別のレコードにわかれている運動時間を、1つの配列として抽出する為のSQL文の作成
  - 運動時間を合算し、分単位から時間単位へ変換するメソッドの作成
