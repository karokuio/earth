#!/usr/bin/env bash

tmux -2 new-session -d -s earth
tmux rename-window -t earth:0 'dev'
tmux select-window -t earth:0
tmux send-keys -t earth:0 'cat welcome' C-m

tmux select-window -t earth:0
tmux -2 attach-session -t earth
