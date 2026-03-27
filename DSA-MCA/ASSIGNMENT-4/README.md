<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Bubble Sort in C — README</title>
<style>
  :root {
    --bg: #f9f8f6;
    --surface: #ffffff;
    --surface2: #f1efe8;
    --border: rgba(0,0,0,0.1);
    --text: #1a1a18;
    --muted: #5f5e5a;
    --hint: #888780;
    --blue: #378ADD;
    --blue-bg: #E6F1FB;
    --blue-text: #0C447C;
    --amber: #EF9F27;
    --amber-bg: #FAEEDA;
    --amber-text: #633806;
    --red: #E24B4A;
    --red-bg: #FCEBEB;
    --red-text: #791F1F;
    --green: #1D9E75;
    --green-bg: #E1F5EE;
    --green-text: #085041;
    --radius: 8px;
    --radius-lg: 12px;
  }
  @media (prefers-color-scheme: dark) {
    :root {
      --bg: #1c1c1a;
      --surface: #252523;
      --surface2: #2c2c2a;
      --border: rgba(255,255,255,0.1);
      --text: #e8e6df;
      --muted: #b4b2a9;
      --hint: #888780;
      --blue-bg: #0C447C;
      --blue-text: #B5D4F4;
      --amber-bg: #633806;
      --amber-text: #FAC775;
      --red-bg: #791F1F;
      --red-text: #F7C1C1;
      --green-bg: #085041;
      --green-text: #9FE1CB;
    }
  }
  * { box-sizing: border-box; margin: 0; padding: 0; }
  body { background: var(--bg); color: var(--text); font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', sans-serif; font-size: 15px; line-height: 1.7; }
  .page { max-width: 860px; margin: 0 auto; padding: 48px 24px 80px; }

  h1 { font-size: 32px; font-weight: 600; margin-bottom: 8px; }
  h2 { font-size: 20px; font-weight: 600; margin: 48px 0 16px; padding-bottom: 8px; border-bottom: 0.5px solid var(--border); }
  h3 { font-size: 16px; font-weight: 600; margin: 28px 0 10px; }
  p { margin-bottom: 12px; color: var(--text); }
  blockquote { border-left: 3px solid var(--blue); padding: 10px 16px; background: var(--blue-bg); border-radius: 0 var(--radius) var(--radius) 0; color: var(--blue-text); font-style: italic; margin: 16px 0; }
  hr { border: none; border-top: 0.5px solid var(--border); margin: 32px 0; }
  code { font-family: 'SF Mono', 'Fira Code', monospace; font-size: 13px; background: var(--surface2); padding: 2px 6px; border-radius: 4px; }
  pre { background: var(--surface2); border: 0.5px solid var(--border); border-radius: var(--radius-lg); padding: 18px 20px; overflow-x: auto; margin: 16px 0; }
  pre code { background: none; padding: 0; font-size: 13px; line-height: 1.7; }
  ul, ol { padding-left: 20px; margin-bottom: 12px; }
  li { margin-bottom: 6px; }
  a { color: var(--blue); }

  table { width: 100%; border-collapse: collapse; margin: 16px 0; font-size: 14px; }
  th { text-align: left; padding: 10px 14px; background: var(--surface2); font-weight: 600; border: 0.5px solid var(--border); }
  td { padding: 9px 14px; border: 0.5px solid var(--border); vertical-align: top; }
  tr:nth-child(even) td { background: var(--surface2); }

  .badge { display: inline-block; font-size: 12px; padding: 2px 10px; border-radius: 20px; font-weight: 500; }
  .badge-blue { background: var(--blue-bg); color: var(--blue-text); }
  .badge-green { background: var(--green-bg); color: var(--green-text); }

  .card { background: var(--surface); border: 0.5px solid var(--border); border-radius: var(--radius-lg); padding: 20px 24px; margin: 20px 0; }

  .stats-grid { display: grid; grid-template-columns: repeat(3, 1fr); gap: 10px; margin: 16px 0; }
  .stat-card { background: var(--surface2); border-radius: var(--radius); padding: 12px; text-align: center; }
  .stat-card strong { display: block; font-size: 22px; font-weight: 600; }
  .stat-card span { font-size: 12px; color: var(--muted); }

  .legend { display: flex; gap: 16px; flex-wrap: wrap; font-size: 13px; color: var(--muted); margin-bottom: 16px; }
  .leg { display: flex; align-items: center; gap: 6px; }
  .leg-sq { width: 13px; height: 13px; border-radius: 3px; flex-shrink: 0; }

  .bar-stage { display: flex; align-items: flex-end; justify-content: center; gap: 8px; height: 190px; margin-bottom: 32px; }
  .bar { display: flex; flex-direction: column; align-items: center; justify-content: flex-end; min-width: 48px; flex: 1; max-width: 66px; border-radius: 6px 6px 0 0; transition: height .38s cubic-bezier(.4,0,.2,1), background .22s; }
  .bar-val { font-size: 13px; font-weight: 600; padding: 6px 0 4px; width: 100%; text-align: center; }
  .bar-idx { font-size: 11px; color: var(--hint); text-align: center; margin-top: 5px; }

  .pass-track { display: flex; gap: 6px; flex-wrap: wrap; margin-bottom: 12px; align-items: center; }
  .pass-pill { font-size: 12px; padding: 3px 11px; border-radius: 20px; border: 0.5px solid var(--border); color: var(--muted); background: var(--surface2); }
  .pass-pill.active { background: var(--blue-bg); color: var(--blue-text); border-color: var(--blue); }
  .pass-pill.done { background: var(--green-bg); color: var(--green-text); border-color: var(--green); }

  .msg-box { font-size: 13px; color: var(--muted); min-height: 42px; background: var(--surface2); border-radius: var(--radius); padding: 10px 14px; margin-bottom: 14px; line-height: 1.5; }

  .controls { display: flex; gap: 8px; flex-wrap: wrap; margin-bottom: 12px; }
  .controls button { font-size: 13px; padding: 7px 16px; border-radius: var(--radius); border: 0.5px solid var(--border); background: var(--surface); color: var(--text); cursor: pointer; transition: background .15s; }
  .controls button:hover { background: var(--surface2); }

  .speed-row { display: flex; align-items: center; gap: 10px; font-size: 13px; color: var(--muted); margin-bottom: 14px; }
  .speed-row input[type=range] { width: 120px; }

  .iter-log { max-height: 140px; overflow-y: auto; border: 0.5px solid var(--border); border-radius: var(--radius); padding: 10px 14px; font-size: 12px; font-family: 'SF Mono', monospace; color: var(--muted); line-height: 1.9; }
  .iter-log .swap-line { color: var(--red-text); }
  .iter-log .pass-head { font-weight: 600; color: var(--text); margin-top: 4px; }

  .checklist li { list-style: none; padding-left: 4px; }
  .checklist li::before { content: "☐ "; color: var(--hint); }

  .note { background: var(--amber-bg); border-left: 3px solid var(--amber); border-radius: 0 var(--radius) var(--radius) 0; padding: 10px 16px; color: var(--amber-text); font-size: 14px; margin: 12px 0; }
</style>
</head>
<body>
<div class="page">

  <h1>🫧 Bubble Sort in C</h1>
  <div style="display:flex;gap:8px;margin:10px 0 20px;flex-wrap:wrap">
    <span class="badge badge-blue">Algorithm</span>
    <span class="badge badge-blue">Sorting</span>
    <span class="badge badge-green">Beginner Friendly</span>
    <span class="badge badge-green">O(n²)</span>
  </div>
  <blockquote>A clean implementation of Bubble Sort — one of the most fundamental sorting algorithms in computer science. Simple to understand, elegant to trace, and the perfect first algorithm to master before moving on to faster sorts.</blockquote>

  <hr>

  <h2>📁 File Structure</h2>
  <pre><code>bubble_sort.c   →  Single source file with sorting logic and user input</code></pre>

  <h2>⚙️ Compile &amp; Run</h2>
  <pre><code>gcc bubble_sort.c -o bubble_sort
./bubble_sort</code></pre>
  <p><strong>Sample interaction:</strong></p>
  <pre><code>Enter number of elements: 5
Enter 5 integers:
64 34 25 12 22
Sorted list in ascending order:
12 22 25 34 64</code></pre>

  <hr>

  <h2>🎬 Interactive Visualizer</h2>
  <p style="color:var(--muted);font-size:14px;margin-bottom:20px">Watch every comparison and swap animate in real time. Use Step to go one operation at a time, or Play to watch the full sort.</p>

  <div class="card">
    <div class="legend">
      <span class="leg"><span class="leg-sq" style="background:#B5D4F4"></span>Unsorted</span>
      <span class="leg"><span class="leg-sq" style="background:#EF9F27"></span>Comparing</span>
      <span class="leg"><span class="leg-sq" style="background:#E24B4A"></span>Swapping</span>
      <span class="leg"><span class="leg-sq" style="background:#9FE1CB"></span>Sorted</span>
    </div>

    <div class="bar-stage" id="stage"></div>
    <div id="bar-indices" style="display:flex;justify-content:center;gap:8px;margin-bottom:18px"></div>

    <div class="pass-track" id="passes"></div>
    <div class="msg-box" id="msg">Press Play to watch bubble sort in action.</div>

    <div class="stats-grid">
      <div class="stat-card"><strong id="s-pass">0</strong><span>Pass</span></div>
      <div class="stat-card"><strong id="s-comp">0</strong><span>Comparisons</span></div>
      <div class="stat-card"><strong id="s-swap">0</strong><span>Swaps</span></div>
    </div>

    <div class="controls">
      <button id="btn-play">▶ Play</button>
      <button id="btn-step">Step →</button>
      <button id="btn-reset">↺ Reset</button>
      <button id="btn-shuffle">Shuffle</button>
    </div>

    <div class="speed-row">
      <span>Speed</span>
      <input type="range" min="1" max="5" value="2" id="speed" step="1">
      <span id="speed-lbl">2x</span>
    </div>

    <div class="iter-log" id="log"></div>
  </div>

  <hr>

  <h2>🔍 How It Works — Pass by Pass</h2>
  <p>Bubble Sort repeatedly compares adjacent pairs and swaps them if they're in the wrong order. Each full pass pushes the largest unsorted element to its final position at the end — like a bubble rising to the surface.</p>

  <h3>Example: Sorting <code>[38, 27, 43, 15, 9]</code></h3>

  <h3>Pass 1 — 43 bubbles to the end</h3>
  <pre><code>Initial:  [ 38 | 27 | 43 | 15 |  9 ]
           ----+----
Step 1:   Compare 38 & 27  →  38 > 27  →  SWAP
          [ 27 | 38 | 43 | 15 |  9 ]
                ----+----
Step 2:   Compare 38 & 43  →  38 < 43  →  no swap
          [ 27 | 38 | 43 | 15 |  9 ]
                     ----+----
Step 3:   Compare 43 & 15  →  43 > 15  →  SWAP
          [ 27 | 38 | 15 | 43 |  9 ]
                          ----+----
Step 4:   Compare 43 &  9  →  43 >  9  →  SWAP
          [ 27 | 38 | 15 |  9 | 43 ]  ✅ 43 settled</code></pre>

  <h3>Pass 2 — 38 bubbles to position</h3>
  <pre><code>Start:    [ 27 | 38 | 15 |  9 | 43✅]
Step 1:   Compare 27 & 38  →  no swap
Step 2:   Compare 38 & 15  →  SWAP  →  [ 27 | 15 | 38 |  9 | 43✅]
Step 3:   Compare 38 &  9  →  SWAP  →  [ 27 | 15 |  9 | 38✅| 43✅]</code></pre>

  <h3>Pass 3 — 27 bubbles to position</h3>
  <pre><code>Start:    [ 27 | 15 |  9 | 38✅| 43✅]
Step 1:   Compare 27 & 15  →  SWAP  →  [ 15 | 27 |  9 | 38✅| 43✅]
Step 2:   Compare 27 &  9  →  SWAP  →  [ 15 |  9 | 27✅| 38✅| 43✅]</code></pre>

  <h3>Pass 4 — Final comparison</h3>
  <pre><code>Start:    [ 15 |  9 | 27✅| 38✅| 43✅]
Step 1:   Compare 15 &  9  →  SWAP  →  [  9 | 15✅| 27✅| 38✅| 43✅] ✅</code></pre>

  <h3>Summary of all passes</h3>
  <table>
    <tr><th>Pass</th><th>Array after pass</th><th>Element settled</th></tr>
    <tr><td>Start</td><td><code>[38, 27, 43, 15, 9]</code></td><td>—</td></tr>
    <tr><td>1</td><td><code>[27, 38, 15, 9, 43]</code></td><td>43</td></tr>
    <tr><td>2</td><td><code>[27, 15, 9, 38, 43]</code></td><td>38</td></tr>
    <tr><td>3</td><td><code>[15, 9, 27, 38, 43]</code></td><td>27</td></tr>
    <tr><td>4</td><td><code>[9, 15, 27, 38, 43]</code></td><td>15, 9</td></tr>
  </table>

  <hr>

  <h2>🧠 Code Breakdown</h2>
  <pre><code>for (i = 0; i &lt; n - 1; i++) {          // n-1 passes needed
    for (j = 0; j &lt; n - i - 1; j++) { // shrinks each pass (last i are sorted)
        if (arr[j] &gt; arr[j + 1]) {     // wrong order?
            temp = arr[j];
            arr[j] = arr[j + 1];       // swap them
            arr[j + 1] = temp;
        }
    }
}</code></pre>

  <table>
    <tr><th>Part</th><th>Purpose</th></tr>
    <tr><td><code>i</code> outer loop</td><td>Counts passes (n-1 total)</td></tr>
    <tr><td><code>j</code> inner loop</td><td>Slides comparison window forward</td></tr>
    <tr><td><code>n - i - 1</code></td><td>Skips already-sorted tail — avoids redundant work</td></tr>
    <tr><td><code>temp</code> swap</td><td>Classic 3-variable in-place swap — no extra array needed</td></tr>
  </table>

  <hr>

  <h2>📊 Time &amp; Space Complexity</h2>
  <table>
    <tr><th>Case</th><th>Time</th><th>When</th></tr>
    <tr><td>Best</td><td><code>O(n)</code></td><td>Already sorted (with early-exit flag)</td></tr>
    <tr><td>Average</td><td><code>O(n²)</code></td><td>Random input — ~n²/2 comparisons</td></tr>
    <tr><td>Worst</td><td><code>O(n²)</code></td><td>Reverse sorted — every pair swaps</td></tr>
    <tr><td>Space</td><td><code>O(1)</code></td><td>In-place, only 1 temp variable</td></tr>
  </table>
  <div class="note">This implementation does NOT include early-exit optimization. Add a <code>swapped</code> flag to achieve O(n) best case.</div>

  <h3>How it scales (worst case)</h3>
  <table>
    <tr><th>Elements (n)</th><th>Comparisons</th></tr>
    <tr><td>10</td><td>~45</td></tr>
    <tr><td>100</td><td>~4,950</td></tr>
    <tr><td>1,000</td><td>~499,500</td></tr>
    <tr><td>10,000</td><td>~49,995,000</td></tr>
  </table>

  <hr>

  <h2>🌍 Real-World Impact of Sorting</h2>
  <table>
    <tr><th>Domain</th><th>How Sorting Is Used</th></tr>
    <tr><td>E-Commerce</td><td>Sorting products by price, rating, relevance in milliseconds</td></tr>
    <tr><td>Search Engines</td><td>Google ranks billions of web pages by relevance score</td></tr>
    <tr><td>Banking &amp; Finance</td><td>Transactions sorted by timestamp for ledger and fraud detection</td></tr>
    <tr><td>Operating Systems</td><td>Processes sorted by priority in the CPU scheduling queue</td></tr>
    <tr><td>Databases</td><td>SQL ORDER BY uses merge sort or timsort internally</td></tr>
    <tr><td>Leaderboards</td><td>Game servers, exam results, sports standings ranked in real time</td></tr>
    <tr><td>Contact Lists</td><td>Your phone sorts contacts alphabetically on every new addition</td></tr>
    <tr><td>Navigation / Maps</td><td>Route planning sorts paths by distance — underpins Dijkstra's algorithm</td></tr>
    <tr><td>Medical Records</td><td>Hospitals sort patients by urgency or diagnosis for triage</td></tr>
    <tr><td>File Managers</td><td>Sorting files by name, date, or size behind the scenes</td></tr>
  </table>
  <blockquote>Bubble Sort specifically is still taught because it is the most human-intuitive algorithm — you can "see" it working. It appears in embedded systems and microcontrollers where n is always small and simplicity beats speed.</blockquote>

  <hr>

  <h2>🧠 Knowledge Gained</h2>

  <h3>Core Programming Concepts</h3>
  <ul>
    <li><strong>Nested Loops</strong> — The outer loop controls passes, the inner loop scans. Understanding how they interact is fundamental to any O(n²) algorithm.</li>
    <li><strong>In-Place Swapping</strong> — The classic 3-variable swap (<code>temp = a; a = b; b = temp</code>) appears in many future algorithms.</li>
    <li><strong>Variable-Length Arrays (VLA)</strong> — <code>int arr[n]</code> where n is entered at runtime — a C99 feature that allocates stack memory dynamically.</li>
    <li><strong>Array Indexing Safety</strong> — Careful use of <code>j</code> and <code>j+1</code> without going out of bounds, controlled by <code>n - i - 1</code>.</li>
  </ul>

  <h3>Algorithm Design Concepts</h3>
  <ul>
    <li><strong>Comparison-Based Sorting</strong> — Bubble sort shows why any comparison-based sort needs at minimum O(n log n) — its O(n²) makes the motivation for better algorithms concrete.</li>
    <li><strong>Loop Invariant</strong> — After pass i, the last i elements are guaranteed to be in final position. Your first exposure to formal algorithm correctness reasoning.</li>
    <li><strong>Adaptive Algorithms</strong> — A swapped flag can detect a sorted array in one pass — algorithms can be made smarter by observing data.</li>
    <li><strong>Eliminating Redundant Work</strong> — <code>j &lt; n - i - 1</code> avoids re-checking sorted elements. Removing it gives correct results but doubles the work.</li>
  </ul>

  <h3>Software Engineering Habits</h3>
  <ul>
    <li><strong>Edge Case Testing</strong> — What happens with n=1? Reverse sorted? All duplicates? These are key engineering thinking habits.</li>
    <li><strong>Separation of Concerns</strong> — <code>bubbleSort()</code> is a pure function; <code>main()</code> handles I/O. Good modular design from day one.</li>
    <li><strong>Benchmarking Mindset</strong> — Feeling O(n²) slowness on large inputs makes you understand why merge sort (O(n log n)) matters in practice.</li>
  </ul>

  <hr>

  <h2>📌 Algorithm Comparison</h2>
  <table>
    <tr><th>Algorithm</th><th>Best</th><th>Average</th><th>Worst</th><th>Stable</th><th>In-Place</th></tr>
    <tr><td><strong>Bubble Sort</strong></td><td>O(n)*</td><td>O(n²)</td><td>O(n²)</td><td>✅</td><td>✅</td></tr>
    <tr><td>Selection Sort</td><td>O(n²)</td><td>O(n²)</td><td>O(n²)</td><td>❌</td><td>✅</td></tr>
    <tr><td>Insertion Sort</td><td>O(n)</td><td>O(n²)</td><td>O(n²)</td><td>✅</td><td>✅</td></tr>
    <tr><td>Merge Sort</td><td>O(n log n)</td><td>O(n log n)</td><td>O(n log n)</td><td>✅</td><td>❌</td></tr>
    <tr><td>Quick Sort</td><td>O(n log n)</td><td>O(n log n)</td><td>O(n²)</td><td>❌</td><td>✅</td></tr>
  </table>
  <p style="font-size:13px;color:var(--muted)">*With early-exit optimization</p>

  <hr>

  <h2>🔼 Suggested Next Steps</h2>
  <ul class="checklist">
    <li>Add a <code>swapped</code> flag for early-exit — achieves O(n) best case</li>
    <li>Sort in descending order — change <code>arr[j] &gt; arr[j+1]</code> to <code>arr[j] &lt; arr[j+1]</code></li>
    <li>Add swap and comparison counters, print them after sorting</li>
    <li>Implement Selection Sort and Insertion Sort — compare pass-by-pass behavior</li>
    <li>Benchmark against C's built-in <code>qsort()</code> on 10,000 elements</li>
    <li>Print the array after every pass as ASCII to visualize in the terminal</li>
  </ul>

  <hr>

  <p style="text-align:center;color:var(--muted);font-size:13px;margin-top:40px">
    <em>"Bubble sort is the 'Hello World' of algorithms — not because it's useful, but because it makes you think about what sorting even means."</em>
  </p>

</div>

<script>
const DELAYS=[900,580,350,180,70];
let arr=[38,27,43,15,9,31,52,7];
let steps=[],idx=0,playing=false,timer=null;
let comps=0,swaps=0,curPass=0;

const C={normal:'#B5D4F4',compare:'#EF9F27',swap:'#E24B4A',sorted:'#9FE1CB'};
const CT={normal:'#0C447C',compare:'#633806',swap:'#791F1F',sorted:'#085041'};

function buildSteps(a){
  const ar=[...a],n=ar.length,out=[],si=new Set();
  out.push({ar:[...ar],hi:[],state:'idle',si:new Set(),msg:'Starting — array: ['+ar.join(', ')+']',pass:0});
  for(let i=0;i<n-1;i++){
    let swapped=false;
    out.push({ar:[...ar],hi:[],state:'idle',si:new Set(si),msg:'Pass '+(i+1)+' begins — scanning index 0 to '+(n-i-2),pass:i+1,passStart:true});
    for(let j=0;j<n-i-1;j++){
      out.push({ar:[...ar],hi:[j,j+1],state:'compare',si:new Set(si),msg:'Comparing index '+j+' ('+ar[j]+') and index '+(j+1)+' ('+ar[j+1]+')',pass:i+1,j});
      if(ar[j]>ar[j+1]){
        [ar[j],ar[j+1]]=[ar[j+1],ar[j]];swapped=true;
        out.push({ar:[...ar],hi:[j,j+1],state:'swap',si:new Set(si),msg:'Swap! '+ar[j+1]+' > '+ar[j]+' — positions exchanged',pass:i+1,j,wasSwap:true});
      } else {
        out.push({ar:[...ar],hi:[j,j+1],state:'ok',si:new Set(si),msg:'No swap — '+ar[j]+' ≤ '+ar[j+1]+', already in order',pass:i+1,j,wasSwap:false});
      }
    }
    si.add(n-1-i);
    out.push({ar:[...ar],hi:[n-1-i],state:'settled',si:new Set(si),msg:'Pass '+(i+1)+' done — '+ar[n-1-i]+' is in its final position',pass:i+1,passEnd:true,swapped});
    if(!swapped){
      const all=new Set();for(let k=0;k<n;k++)all.add(k);
      out.push({ar:[...ar],hi:[],state:'done',si:all,msg:'No swaps in pass '+(i+1)+' — list is fully sorted!',pass:i+1});
      return out;
    }
  }
  const all=new Set();for(let k=0;k<ar.length;k++)all.add(k);
  out.push({ar:[...ar],hi:[],state:'done',si:all,msg:'Sorted! All '+ar.length+' elements are in order.',pass:arr.length-1});
  return out;
}

function getDelay(){return DELAYS[+document.getElementById('speed').value-1];}

function renderBars(s){
  const stage=document.getElementById('stage');
  const maxV=Math.max(...s.ar);
  stage.innerHTML='';
  s.ar.forEach((v,i)=>{
    const h=Math.max(24,Math.round((v/maxV)*170));
    let bg=C.normal,tc=CT.normal;
    if(s.si&&s.si.has(i)){bg=C.sorted;tc=CT.sorted;}
    if(s.hi&&s.hi.includes(i)){
      if(s.state==='swap'){bg=C.swap;tc=CT.swap;}
      else if(s.state==='compare'||s.state==='ok'){bg=C.compare;tc=CT.compare;}
      else if(s.state==='settled'){bg=C.sorted;tc=CT.sorted;}
    }
    const bar=document.createElement('div');
    bar.className='bar';
    bar.style.cssText='height:'+h+'px;background:'+bg;
    const vl=document.createElement('div');
    vl.className='bar-val';vl.style.color=tc;vl.textContent=v;
    bar.appendChild(vl);
    stage.appendChild(bar);
  });
  const idxRow=document.getElementById('bar-indices');
  idxRow.innerHTML='';
  s.ar.forEach((_,i)=>{
    const d=document.createElement('div');
    d.className='bar-idx';
    d.style.cssText='flex:1;max-width:66px;min-width:48px;text-align:center;font-size:11px;color:var(--hint)';
    d.textContent='['+i+']';
    idxRow.appendChild(d);
  });
}

function renderPasses(s){
  const pt=document.getElementById('passes');
  pt.innerHTML='<span style="font-size:12px;color:var(--muted);margin-right:4px">Passes:</span>';
  const total=arr.length-1;
  for(let i=1;i<=total;i++){
    const pill=document.createElement('span');
    pill.className='pass-pill'+(i===s.pass?' active':i<s.pass?' done':'');
    pill.textContent='Pass '+i;
    pt.appendChild(pill);
  }
}

function logStep(s){
  if(!s.passStart&&!s.passEnd&&s.wasSwap===undefined)return;
  const log=document.getElementById('log');
  const line=document.createElement('div');
  if(s.passStart){line.className='pass-head';line.textContent='Pass '+s.pass+':';}
  else if(s.passEnd){line.textContent='  End of pass '+s.pass+' → '+s.ar[s.ar.length-s.pass]+' settled';}
  else if(s.wasSwap===true){line.className='swap-line';line.textContent='  j='+s.j+': SWAP';}
  else{line.textContent='  j='+s.j+': no swap';}
  log.appendChild(line);
  log.scrollTop=log.scrollHeight;
}

function applyStep(s){
  if(s.state==='compare'||s.state==='ok')comps++;
  if(s.state==='swap')swaps++;
  curPass=s.pass||curPass;
  document.getElementById('s-comp').textContent=comps;
  document.getElementById('s-swap').textContent=swaps;
  document.getElementById('s-pass').textContent=curPass;
  document.getElementById('msg').textContent=s.msg;
  renderBars(s);renderPasses(s);logStep(s);
}

function reset(){
  clearInterval(timer);playing=false;
  steps=buildSteps(arr);idx=0;comps=0;swaps=0;curPass=0;
  document.getElementById('s-comp').textContent=0;
  document.getElementById('s-swap').textContent=0;
  document.getElementById('s-pass').textContent=0;
  document.getElementById('log').innerHTML='';
  document.getElementById('btn-play').textContent='▶ Play';
  applyStep(steps[0]);
}

function doStep(){
  if(idx>=steps.length-1){clearInterval(timer);playing=false;document.getElementById('btn-play').textContent='▶ Play';return;}
  idx++;applyStep(steps[idx]);
}

document.getElementById('btn-play').addEventListener('click',()=>{
  if(idx>=steps.length-1)reset();
  if(playing){clearInterval(timer);playing=false;document.getElementById('btn-play').textContent='▶ Play';}
  else{
    playing=true;document.getElementById('btn-play').textContent='⏸ Pause';
    timer=setInterval(()=>{doStep();if(idx>=steps.length-1){clearInterval(timer);playing=false;document.getElementById('btn-play').textContent='▶ Play';}},getDelay());
  }
});
document.getElementById('btn-step').addEventListener('click',()=>{clearInterval(timer);playing=false;document.getElementById('btn-play').textContent='▶ Play';doStep();});
document.getElementById('btn-reset').addEventListener('click',reset);
document.getElementById('btn-shuffle').addEventListener('click',()=>{
  arr=Array.from({length:8},()=>Math.floor(Math.random()*55)+6);reset();
});
document.getElementById('speed').addEventListener('input',function(){
  document.getElementById('speed-lbl').textContent=this.value+'x';
  if(playing){clearInterval(timer);timer=setInterval(()=>{doStep();if(idx>=steps.length-1){clearInterval(timer);playing=false;document.getElementById('btn-play').textContent='▶ Play';}},getDelay());}
});
reset();
</script>
</body>
</html>
