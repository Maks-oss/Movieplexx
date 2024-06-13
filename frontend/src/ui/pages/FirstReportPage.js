import * as React from 'react';
import { styled } from '@mui/material/styles';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell, { tableCellClasses } from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import Box from '@mui/material/Box';
import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import FormControl from '@mui/material/FormControl';
import Select from '@mui/material/Select';
import {Stack} from '@mui/material';
import {fetchApi} from "../../utils/ApiCalls";
import { useMovieplexxContext } from '../../utils/MovieplexxContext';
const StyledTableCell = styled(TableCell)(({ theme }) => ({
    [`&.${tableCellClasses.head}`]: {
        backgroundColor: theme.palette.common.black,
        color: theme.palette.common.white,
    },
    [`&.${tableCellClasses.body}`]: {
        fontSize: 14,
    },
}));
const ReportTable = ({data}) => {
    return (

        <TableContainer component={Paper} >
            <Table sx={{minWidth: 650}} aria-label="simple table">
                <TableHead>
                    <TableRow>
                        <StyledTableCell>Customer</StyledTableCell>
                        <StyledTableCell align="right">Total Money Spent</StyledTableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {data.map((report, index) => (
                        <TableRow
                            key={index}
                            sx={{'&:last-child td, &:last-child th': {border: 0}}}
                        >
                            <TableCell component="th" scope="row">
                                {report.customerName}
                            </TableCell>
                            <TableCell align="right">{report.totalPrice.toFixed(2)}</TableCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
        </TableContainer>
    )
}
const CinemaPicker = ({branches, cinema, onChange}) => {
    return (
        <FormControl variant="standard" sx={{m: 1, maxWidth: 120}}>
            <InputLabel id="demo-simple-select-standard-label">Cinema Branch</InputLabel>
            <Select
                labelId="demo-simple-select-standard-label"
                id="demo-simple-select-standard"
                value={cinema}
                onChange={onChange}
                label="Cinema"
            >
                { branches.map((branch) => (
                    <MenuItem value={branch}>{branch}</MenuItem>
                ))
                }
            </Select>
        </FormControl>
    );
}

function FirstReportPage() {
    const [report, setReport] = React.useState(null);
    const [currCinema, setCurrCinema] = React.useState('');
    const { endpoints } = useMovieplexxContext();


    React.useEffect(() => {
        fetchApi(`http://localhost:5433${endpoints.getReportMaks}`).then((data) => {
            setReport(data)
            setCurrCinema(Object.keys(data)[0])
        })
    }, [endpoints])

    const handleChange = (event) => {
        console.log('On handle change: ',event.target.value)
        setCurrCinema(event.target.value);
    };
    if (report) {
        return (
            <Box
                display="flex"
                justifyContent="center"
                alignItems="center"
                minHeight="100vh"
            >
                <Stack spacing={2}>
                    <CinemaPicker branches={Object.keys(report)} cinema={currCinema} onChange={handleChange}/>
                    <ReportTable data={report[currCinema]}/>
                </Stack>
            </Box>

        );
    }
}

export default FirstReportPage
