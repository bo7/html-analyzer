FROM nginx:alpine

# Copy the HTML files to the nginx html directory
COPY *.html /usr/share/nginx/html/
COPY *.java /usr/share/nginx/html/

# Expose port 6464
EXPOSE 6464

# Configure nginx to listen on port 6464
RUN echo 'server { \
    listen 6464; \
    server_name localhost; \
    location / { \
        root /usr/share/nginx/html; \
        index simple_ascii_frontend.html; \
    } \
}' > /etc/nginx/conf.d/default.conf
