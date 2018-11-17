# Rosbank Tech.Madness

## Команда 6


### Креденции доступа к приложению

user: admin
password: 123


### Сборка приложения
```bash
chmod +x asm.sh

./asm.sh

# рядом в корне появится app.jar
```

### Деплой приложения в docker registry
```bash
chmod +x push.sh
# в файлике push.sh наверзу пишем версию, которую деплоим

# создаем файлик pwd.txt и кладем в него пароль от Dockerhub
# а в переменной D_LOGIN -- логин
export D_LOGIN=yattbot

./push.sh
```
