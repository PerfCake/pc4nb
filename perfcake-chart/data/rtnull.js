var rtnull = [ [ 'Time', 'Result', 'Average' ] ];
var ho = (new Date(0)).getHours();
var offset = - (ho >= 12 ? ho - 24 : ho) * 60 * 60 * 1000;

rtnull.push([new Date(9517 + offset), 0.152185, 0.24539026934673366]);
rtnull.push([new Date(10003 + offset), 1.236949, 0.23555333057851222]);
