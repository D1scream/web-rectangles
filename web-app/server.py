#!/usr/bin/env python3
"""
HTTP сервер для веб-приложения игры в квадраты
Запуск: python server.py
"""

import http.server
import socketserver
import os
import webbrowser
from urllib.parse import urlparse

class CustomHTTPRequestHandler(http.server.SimpleHTTPRequestHandler):
    def __init__(self, *args, **kwargs):
        super().__init__(*args, directory=os.path.join(os.path.dirname(__file__), 'public'), **kwargs)
    
    def end_headers(self):
        # Добавляем CORS заголовки
        self.send_header('Access-Control-Allow-Origin', '*')
        self.send_header('Access-Control-Allow-Methods', 'GET, POST, OPTIONS')
        self.send_header('Access-Control-Allow-Headers', 'Content-Type')
        super().end_headers()

    def do_OPTIONS(self):
        # Обрабатываем preflight запросы
        self.send_response(200)
        self.end_headers()
    
    def do_GET(self):
        # Если запрашивается config.js, генерируем его с API_URL
        if self.path == '/js/config.js':
            api_url = os.environ.get('API_URL', 'http://localhost:8080')
            config_content = f'window.APP_CONFIG = {{ API_URL: "{api_url}" }};'
            
            self.send_response(200)
            self.send_header('Content-type', 'application/javascript')
            self.end_headers()
            self.wfile.write(config_content.encode('utf-8'))
            return
        
        super().do_GET()

def main():
    PORT = 3000
    
    # Переходим в директорию со скриптом
    os.chdir(os.path.dirname(os.path.abspath(__file__)))
    
    with socketserver.TCPServer(("", PORT), CustomHTTPRequestHandler) as httpd:
        print(f"Web App server started on port {PORT}")
        print(f"Open http://localhost:{PORT} to play the game")
        print("Make sure the backend API is running on port 8080")
        print("Press Ctrl+C to stop the server")
        
        try:
            httpd.serve_forever()
        except KeyboardInterrupt:
            print("\nServer stopped")

if __name__ == "__main__":
    main()
