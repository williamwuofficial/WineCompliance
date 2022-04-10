import * as React from 'react';
import { observer } from 'mobx-react';
import { RouteComponentProps } from 'react-router';
import { action, makeObservable, observable, runInAction } from 'mobx';
import { Link } from 'react-router-dom';
import { Button, Col, Nav, NavItem, NavLink, Row, Spinner, TabContent, TabPane, } from 'reactstrap';
import '../../css/wine-details-page.css'
import { ReactComponent as RedWIcon } from '../../Assets/Icons/W-Red.svg';
import { ReactComponent as EditIcon } from '../../Assets/Icons/Edit.svg';
import { ReactComponent as ArrowLeftIcon } from '../../Assets/Icons/Arrow-Left.svg';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import WineEntity from '../../Model/WineEntity';
import WineBreakdownEntity from '../../Model/WineBreakdownEntity';
import { ExpandableTableSection } from '../Components/ExpandableTable';

enum TabIndex {
    Year = "Year",
    Variety = "Variety",
    Region = "Region",
    YearVariety = "Year & Variety"
}

@observer
export default class WineDetailsPage extends React.Component<RouteComponentProps<{ lotCode: string }>> {

    constructor(props: any) {
        super(props);
        makeObservable(this);
    }

    @observable
    private wine: WineEntity;

    @observable
    private activeTab: TabIndex = TabIndex.Year;

    @action
    setActiveTab = (tabNumber: TabIndex) => {
        this.activeTab = tabNumber;
    }

    async componentDidMount() {
        try {
            const response = await (await fetch('/api/breakdown/wine/' + this.props.match.params.lotCode)).json();
            const yearData = await (await fetch('/api/breakdown/year/' + this.props.match.params.lotCode)).json();
            const regionData = await (await fetch('/api/breakdown/variety/' + this.props.match.params.lotCode)).json();
            const varietyData = await (await fetch('/api/breakdown/region/' + this.props.match.params.lotCode)).json();
            const yearVarietyData = await (await fetch('/api/breakdown/year-variety/' + this.props.match.params.lotCode)).json();
            
            runInAction(() => {
                this.wine = new WineEntity(response);
                this.wine.setBreakdownPercentages(new WineBreakdownEntity(yearData));
                this.wine.setBreakdownPercentages(new WineBreakdownEntity(regionData));
                this.wine.setBreakdownPercentages(new WineBreakdownEntity(varietyData));
                this.wine.setBreakdownPercentages(new WineBreakdownEntity(yearVarietyData));
            });
        } catch {
            this.props.history.push("/404")
        }
    }

    private renderHeaderSection = () => {
        return (
            <header>
                <ToastContainer
                    position="top-left"
                    autoClose={5000}
                    hideProgressBar={false}
                    newestOnTop
                    closeOnClick
                    rtl={false}
                    pauseOnFocusLoss
                    draggable
                    pauseOnHover
                />
                <div className="d-flex justify-content-between">
                    <div>
                        <Button color={'white'}>
                            <Link to="/"><ArrowLeftIcon/></Link>
                        </Button>
                        <div className="d-flex align-middle">
                            <RedWIcon className='red-w-icon m-auto align-middle'/>
                            <h1 className='ps-2 m-auto fw-light'>{this.wine?.lotCode}</h1>
                        </div>
                    </div>
                    <div>
                        <Button color={'white'} 
                                onClick={() => toast("Feature Coming Soon...")}>
                            <EditIcon />
                        </Button>
                    </div>
                </div>
                <h4 className="fw-light">
                    {this.wine?.description ?? "No description"}
                </h4>
            </header>
        );
    }

    private renderDescriptionSection = () => {
        return (
            <section className='wine-details py-4 px-3 fw-normal'>
                <Row>
                    <Col>Volume</Col>
                    <Col className='text-end fw-light'>
                        {this.wine?.volume.toLocaleString()} L
                    </Col>
                </Row>
                <Row>
                    <Col>Tank code</Col>
                    <Col className='text-end fw-light'>
                        {this.wine?.tankCode}
                    </Col>
                </Row>
                <Row>
                    <Col>Product state</Col>
                    <Col className='text-end fw-light'>
                        {this.wine?.productState ?? "Unknown state"}
                    </Col>
                </Row>
                <Row>
                    <Col>Owner</Col>
                    <Col className='text-end fw-light'>
                        {this.wine?.ownerName}
                    </Col>
                </Row>
            </section>
        );
    }


    private renderBreakdownTable = () => {
        return (
            <section className='wine-breakdown'>
                <Nav tabs>
                    {
                        (Object.keys(TabIndex) as Array<keyof typeof TabIndex>).map((key) =>
                            <NavItem key={key}>
                                <NavLink
                                    className={this.activeTab === TabIndex[key] ? "active" : ""}
                                    onClick={() => this.setActiveTab(TabIndex[key])}
                                >
                                    {TabIndex[key]}
                                </NavLink>
                            </NavItem>
                        )
                    }
                </Nav>
                <TabContent activeTab={this.activeTab}>
                    {
                        (Object.keys(TabIndex) as Array<keyof typeof TabIndex>).map((key) =>
                            <TabPane tabId={TabIndex[key]} key={key}>
                                <Row>
                                    <Col sm="12">
                                        <ExpandableTableSection
                                            wineBreakdown={
                                                key === TabIndex.Year ? this.wine?.yearBreakdown
                                                : key === TabIndex.Variety ? this.wine?.varietyBreakdown
                                                : key === TabIndex.Region ? this.wine?.regionBreakdown
                                                : this.wine?.yearVarietyBreakdown
                                            }
                                        />
                                    </Col>
                                </Row>
                            </TabPane>
                        )
                    }
                </TabContent>
            </section>
        );
    }

    render() {
        let dataLoaded = false;
        if (this.wine
            && this.wine.yearBreakdown
            && this.wine.regionBreakdown
            && this.wine.varietyBreakdown
            && this.wine.yearVarietyBreakdown) {
            dataLoaded = true;
        }

        return (
            !dataLoaded ?
                <div className={'d-flex justify-content-center align-items-center h-100'}>
                    <Spinner />
                </div>
                :
                <div className="wine-details-page">
                    {this.renderHeaderSection()}
                    {this.renderDescriptionSection()}
                    {this.renderBreakdownTable()}
                </div>
        );
    }

} 