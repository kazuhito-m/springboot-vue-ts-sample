springboot-vue-ts-sample
========================

# What's this ?

以下の組み合わせで「プロジェクトを始められる」サンプル構成。

- server side
  - Java
  - SpringBoot
  - Doma2
  - PostgreSQL
- front end
  - Nust.js
  - Vue.js
  - TypeScript
  - axios

このプロジェクトは「出来る限り自力で更新していくもの」とし、「毎回、サイト等から最新を取得し、手順を持って組み直す」という方針でとする。

## 更新方法

1. SpringBoot本家サイトから「最新のテンプレート」を作成し取得
    1. [こちらのサイト](https://start.spring.io)でプロジェクト一式をつくりダウンロード
    0. 名前は `com.github.kazuhito_m.mysample` で `gradle` , `java` , 最新, 拡張に `web` を指定
0. `Isolating the Domain` からソースを落としてきてマージ
    1. `src` を `mysample` にクラス群をコピー、 `Application` クラスはこちらのものに置き換え
    0. `infrastractur` 層を `Doma2` 用に書き換え
        1. ここは「過去のコミット履歴」から収集(少し煩雑なので)
0. `vue-cli` の最新を入れた状態でプロジェクトを作って追加
    1. `npm install -g @vue/cli` でコマンドをインストール
    0. `vue create springboot-vue-ts-sample-front` でプロジェクト作成
        1. `npm run clean` など「npmで使うコマンド」を `package.json` に設定する
    0. `mv springboot-vue-ts-sample-front front` でリネーム
    0. `build.bradle` に「front以下をnpmでビルドする」設定を追加
        1. ここは「過去のコミット履歴」から収集(少し煩雑なので)

---

0. DockerとFlywayでのDB初期化を追加
0. バージョンの最新化確認
    0. gradle wrapper
    0. Doma2

# Usage


# Auther

[@kazuhito_m](https://twitter.com/kazuhito_m)
